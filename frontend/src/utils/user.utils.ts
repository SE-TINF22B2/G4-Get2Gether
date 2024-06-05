import {EventParticipant} from "../model/user";

export const getUserNameForParticipant = (participant: EventParticipant) => {
  const userName = [participant.firstName, participant.lastName].join(" ");
  if (participant.hasLeft) {
    return userName + " (Verlassen)";
  }
  return userName;
}
