import {BaseWidget, WidgetType} from "./common-widget";
import {SimpleUser} from "./user";

export type ExpenseSplitWidget = BaseWidget & {
  widgetType: WidgetType.EXPENSE_SPLIT;
  entries: ExpenseEntry[];
  debts: Debt[];
}

export type ExpenseEntry = {
  id: string;
  creatorId: string;
  description: string;
  price: number;
  involvedUsers: UsersWithPercentage[];
  percentagePerPerson: number;
  pricePerPerson: number;
}

export type UsersWithPercentage = {
  user: SimpleUser;
  percentage: number;
}

export type Debt = {
  user: SimpleUser;
  debtAmount: number;
}

export type ExpenseEntryAddCommand = {
  description: string;
  price: number;
  involvedUsers: string[];
}

export type ExpenseEntryUpdateCommand = {
  description: string;
  price: number;
  involvedUsers: string[];
}
