export type User = {
  id: string;
  creationDate: string;
  email: string;
  firstName: string;
  lastName: string;
  profilePictureUrl: string;
  settings: UserSettings;
  guest: true | undefined;
}

export type EventParticipant = {
  id: string;
  firstName: string;
  lastName: string;
  profilePictureUrl: string;
  hasLeft: boolean;
}

export type UserSettings = {
  colorMode: ColorMode;
}

export enum ColorMode {
  LIGHT = "LIGHT",
  DARK = "DARK",
  WATER = "WATER",
  GRASSLAND = "GRASSLAND",
  SUNSET = "SUNSET",
  DEVELOPER = "DEVELOPER",
  AUTUMN = "AUTUMN"
}
