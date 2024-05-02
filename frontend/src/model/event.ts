import {BaseWidget} from "./common-widget";

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
  participantIds: string[]; // TODO: may be replaced be array of simple user objects
  widgets: BaseWidget[];
}
