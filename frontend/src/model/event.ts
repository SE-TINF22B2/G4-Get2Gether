import {BaseWidget} from "./common-widget";
import {SimpleUser} from "./user";

export type EventOverview = {
  id: string;
  name: string;
  date: string;
  endDate: string | undefined;
}

export type Event = EventOverview & {
  creationDate: string;
  description: string;
  location: string;
  invitationLink: string | undefined;
  creatorId: string;
  participants: SimpleUser[];
  widgets: BaseWidget[];
}

export type CreateEventCommand = {
  name: string;
  description: string | undefined;
  location: string | undefined;
  date: string | undefined;
  endDate: string | undefined;
}
