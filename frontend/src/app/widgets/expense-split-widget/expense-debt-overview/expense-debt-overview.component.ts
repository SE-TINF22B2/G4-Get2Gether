import {Component, Input} from '@angular/core';
import {Debt} from "../../../../model/expense-split-widget";

@Component({
  selector: 'app-expense-debt-overview',
  templateUrl: './expense-debt-overview.component.html',
  styleUrl: './expense-debt-overview.component.scss'
})
export class ExpenseDebtOverviewComponent {

  @Input()
  debts!: Debt[];

  isOweingMe(debt: Debt): boolean {
    return debt.debtAmount > 0;
  }

  protected readonly Math = Math;
}
