export type UserResponse = {
  id: number;
  userName: string;
  email: string;
  registerTime: string;
};

export type CreateUser = {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  userName: string;
};
