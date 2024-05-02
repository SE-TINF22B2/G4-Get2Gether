import {BaseWidget} from "./common-widget";

export type MapWidget = BaseWidget & {
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
