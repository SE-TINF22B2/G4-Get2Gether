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

    public List<Dept> calculateDeptsForUserId(String userId){
        List<Dept> depts = new ArrayList<>();
        for(ExpenseEntry expenseEntry : entries){
            if(expenseEntry.getCreatorId().equals(userId)){
                // Add every involved user except the buyer to List of dept
                for(UserWithPercentage userWithPercentage: expenseEntry.getInvolvedUsers()){
                    if(!Objects.equals(userWithPercentage.getUserId(), userId)){
                        depts = addDept(depts, userWithPercentage.getUserId(), expenseEntry.getPrice()*userWithPercentage.getPercentage());
                    }
                }
            } else {
                Optional<UserWithPercentage> userWithPercentage = expenseEntry.getInvolvedUsers().stream()
                        .filter(user -> user.getUserId().equals(userId)).findFirst();
                if(userWithPercentage.isPresent()){
                    // Add the Dept of the user to the Dept List
                    // In this case the user for whom we calculate all depts is a involved user and not the payer
                    depts = addDept(depts, expenseEntry.getCreatorId(), expenseEntry.getPrice()*userWithPercentage.get().getPercentage()*-1.0);
                }
            }
        }
        return depts;
    }

    // This method extracts the logic if the deptor is already in the list or not
    private List<Dept> addDept(List<Dept> depts, String debtorId, double deptAmount){
        Optional<Dept> optionalDept = depts.stream()
                .filter(dept -> dept.getUserId().equals(debtorId)).findFirst();

        if(optionalDept.isPresent()){
            // Calculate new deptAmount if already exists
            Dept newDept = new Dept(debtorId, optionalDept.get().getDeptAmount()+deptAmount);
            int index = depts.indexOf(optionalDept.get());
            depts.set(index, newDept);

        } else {
            // Add new dept if not exists already
            depts.add(new Dept(debtorId, deptAmount));
        }
        return depts;
    }
}

