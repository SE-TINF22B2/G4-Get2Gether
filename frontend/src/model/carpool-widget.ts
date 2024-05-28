import {BaseWidget, WidgetType} from "./common-widget";
import {SimpleUser} from "./user";

export type CarpoolWidget = BaseWidget & {
  widgetType: WidgetType.CARPOOL;
  cars: Car[];
}

export type Car = {
  id: string;
  driverId: string;
  driverAdress: string;
  anzahlPlaetze: number;
  riders: Rider[];
}

export type CarAddCommand = {
  driverAdress: string;
  anzahlPlaetze: number;
}

export type CarUpdateCommand = {
  driverAdress: string;
  anzahlPlaetze: number;
}

export type RiderAddCommand = {
  pickupPlace: string;
}


export type Rider = {
  id: string;
  userId: string;
  pickupPlace: string;
}
