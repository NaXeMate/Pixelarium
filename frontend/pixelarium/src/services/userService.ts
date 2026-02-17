import { apiRequest } from "./api";
import type { CreateUser, UserResponse } from "../types/index.ts";

export async function getAllUsers(): Promise<UserResponse[]> {
  return apiRequest<UserResponse[]>("GET", `/users`);
}

export async function getUserById(id: number): Promise<UserResponse> {
  return apiRequest<UserResponse>("GET", `/users/${id}`);
}

export async function getUserByEmail(email: string): Promise<UserResponse> {
  return apiRequest<UserResponse>("GET", `/users/email/${email}`);
}

export async function createUser(userData: CreateUser): Promise<UserResponse> {
  return apiRequest<UserResponse>("POST", `/users`, userData);
}

export async function login(
  email: string,
  password: string,
): Promise<UserResponse> {
  return apiRequest<UserResponse>("POST", `/users/login`, { email, password });
}

export async function updateUser(
  id: number,
  userData: CreateUser,
): Promise<UserResponse> {
  return apiRequest<UserResponse>("PUT", `/users/${id}`, userData);
}

export async function deleteUser(id: number): Promise<void> {
  return apiRequest<void>("DELETE", `/users/${id}`);
}
