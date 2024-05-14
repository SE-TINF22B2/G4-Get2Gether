import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LocationAddCommand, MapWidget} from "../../model/map-widget";
import {Observable} from "rxjs";
import {environment} from "../../environment/environment";
import {FormGroup} from "@angular/forms";
import {EinkaufslisteWidgetComponent} from "../../app/widgets/einkaufsliste-widget/einkaufsliste-widget.component";
import {EntryAddCommand, ShoppingWidget} from "../../model/shoppinglist-widget";

@Injectable({
  providedIn: 'root'
})
export class EinkaufslisteWidgetService {

  constructor(private http: HttpClient) {
  }

  addEntry(eventId: string, widgetId: string, entryAddCommand: EntryAddCommand): Observable<ShoppingWidget> {
    return this.http.post<ShoppingWidget>(`${environment.api}/event/${eventId}/widgets/shopping-list/${widgetId}/entries`, entryAddCommand, {withCredentials: true});
  }
}
