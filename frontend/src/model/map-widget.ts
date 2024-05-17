import {BaseWidget, WidgetType} from "./common-widget";

export type MapWidget = BaseWidget & {
  widgetType: WidgetType.MAP;
  locations: Location[];
}

export type Location = {
  id: string;
  placeId: string;
  name: string;
  address: string;
  latitude: number;
  longitude: number;
}

export type LocationAddCommand = {
  placeId: string;
  name: string;
  address: string;
  latitude: number;
  longitude: number;
}
