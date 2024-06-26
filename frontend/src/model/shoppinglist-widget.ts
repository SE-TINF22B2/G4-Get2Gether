import {BaseWidget} from "./common-widget";

export type ShoppingWidget = BaseWidget & {
  entries: Entry[];
}

export type Entry = {
  id: string;
  checked: boolean;
  creatorId: string;
  description: string;
  amount: string;
  buyerId: string;
}

export type EntryCommand = {
  description: string;
  amount: string;
}

export type EntryCheckCommand = {
  checked: boolean;
}
