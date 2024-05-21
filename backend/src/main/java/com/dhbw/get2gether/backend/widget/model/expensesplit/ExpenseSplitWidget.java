package com.dhbw.get2gether.backend.widget.model.expensesplit;

import com.dhbw.get2gether.backend.widget.model.Widget;
import com.dhbw.get2gether.backend.widget.model.WidgetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class ExpenseSplitWidget extends Widget {
    private final String id;
    private final LocalDateTime creationDate;
    @Builder.Default
    private List<ExpenseEntry> entries = new ArrayList<>();

    @Override
    public WidgetType getWidgetType() {
        return WidgetType.EXPENSE_SPLIT;
    }

    public void addEntry(ExpenseEntry entry) {
        entries.add(entry);
    }

    public boolean removeEntry(ExpenseEntry entry) {
        return entries.remove(entry);
    }

    public boolean replaceEntry(ExpenseEntry oldEntry, ExpenseEntry newEntry) {
        int index = entries.indexOf(oldEntry);
        if (index == -1) {
            return false;
        }
        entries.set(index, newEntry);
        return true;
    }

    public List<Debt> calculateDebtsForUserId(String userId){
        List<Debt> debts = new ArrayList<>();
        for(ExpenseEntry expenseEntry : entries){
            if(expenseEntry.getCreatorId().equals(userId)){
                // Add every involved user except the buyer to List of debt
                for(UserWithPercentage userWithPercentage: expenseEntry.getInvolvedUsers()){
                    if(!Objects.equals(userWithPercentage.getUserId(), userId)){
                        debts = addDebt(debts, userWithPercentage.getUserId(), expenseEntry.getPrice()*userWithPercentage.getPercentage());
                    }
                }
            } else {
                Optional<UserWithPercentage> userWithPercentage = expenseEntry.getInvolvedUsers().stream()
                        .filter(user -> user.getUserId().equals(userId)).findFirst();
                if(userWithPercentage.isPresent()){
                    // Add the Debt of the user to the Debt List
                    // In this case the user for whom we calculate all debts is a involved user and not the payer
                    debts = addDebt(debts, expenseEntry.getCreatorId(), expenseEntry.getPrice()*userWithPercentage.get().getPercentage()*-1.0);
                }
            }
        }
        return debts;
    }

    // This method extracts the logic if the debtor is already in the list or not
    private List<Debt> addDebt(List<Debt> debts, String debtorId, double debtAmount){
        Optional<Debt> optionalDebt = debts.stream()
                .filter(debt -> debt.getUserId().equals(debtorId)).findFirst();

        if(optionalDebt.isPresent()){
            // Calculate new debtAmount if already exists
            Debt newDebt = new Debt(debtorId, optionalDebt.get().getDebtAmount()+debtAmount);
            int index = debts.indexOf(optionalDebt.get());
            debts.set(index, newDebt);

        } else {
            // Add new debt if not exists already
            debts.add(new Debt(debtorId, debtAmount));
        }
        return debts;
    }
}

