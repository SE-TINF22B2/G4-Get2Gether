import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environment/environment";
import {ExpenseEntryAddCommand, ExpenseEntryUpdateCommand, ExpenseSplitWidget} from "../../model/expense-split-widget";

@Injectable({
  providedIn: 'root'
})
export class ExpenseSplitWidgetService {

  constructor(private http: HttpClient) {
  }

  createExpenseEntry(
    eventId: string,
    widgetId: string,
    addCommand: ExpenseEntryAddCommand
  ): Observable<ExpenseSplitWidget> {
    return this.http.post<ExpenseSplitWidget>(`${environment.api}/event/${eventId}/widgets/expense-split/${widgetId}/entries`, addCommand, {withCredentials: true});
  }

  updateExpenseEntry(
    eventId: string,
    widgetId: string,
    entryId: string,
    updateCommand: ExpenseEntryUpdateCommand
  ): Observable<ExpenseSplitWidget> {
    return this.http.patch<ExpenseSplitWidget>(`${environment.api}/event/${eventId}/widgets/expense-split/${widgetId}/entries/${entryId}`, updateCommand, {withCredentials: true});
  }

  deleteExpenseEntry(
    eventId: string,
    widgetId: string,
    entryId: string
  ): Observable<ExpenseSplitWidget> {
    return this.http.delete<ExpenseSplitWidget>(`${environment.api}/event/${eventId}/widgets/expense-split/${widgetId}/entries/${entryId}`, {withCredentials: true});
  }
}
