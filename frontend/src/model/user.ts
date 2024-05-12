export type User = {
  id: string
  creationDate: string
  email: string
  firstName: string
  lastName: string
  profilePictureUrl: string
  guest: true | undefined
}

export type SimpleUser = {
  id: string;
  firstName: string;
  lastName: string;
  profilePictureUrl: string;
}
