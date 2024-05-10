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
  participants: SimpleUser[];
  widgets: BaseWidget[];
}

export type SimpleUser = {
  id: string;
  firstName: string;
  lastName: string;
  profilePictureUrl: string;
}
