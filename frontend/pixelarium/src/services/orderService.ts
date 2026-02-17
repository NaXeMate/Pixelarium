import { apiRequest } from "./api";
import type { CreateOrder, OrderResponse, Status } from "../types/index.ts";

export async function getAllOrders(): Promise<OrderResponse[]> {
  return apiRequest<OrderResponse[]>("GET", `/orders`);
}

export async function getOrderById(id: number): Promise<OrderResponse> {
  return apiRequest<OrderResponse>("GET", `/orders/${id}`);
}

export async function createOrder(
  orderData: CreateOrder,
): Promise<OrderResponse> {
  return apiRequest<OrderResponse>("POST", `/orders`, orderData);
}

export async function updateOrder(
  id: number,
  orderData: CreateOrder,
): Promise<OrderResponse> {
  return apiRequest<OrderResponse>("PUT", `/orders/${id}`, orderData);
}

export async function deleteOrder(id: number): Promise<void> {
  return apiRequest<void>("DELETE", `/orders/${id}`);
}

export async function getOrdersByUserId(
  userId: number,
): Promise<OrderResponse[]> {
  return apiRequest<OrderResponse[]>("GET", `/orders/user/${userId}`);
}

export async function getOrdersByStatus(
  status: Status,
): Promise<OrderResponse[]> {
  return apiRequest<OrderResponse[]>("GET", `/orders/status/${status}`);
}

export async function getOrdersByDate(date: string): Promise<OrderResponse[]> {
  return apiRequest<OrderResponse[]>("GET", `/orders/date/${date}`);
}

export async function changeOrderStatus(
  id: number,
  status: Status,
): Promise<OrderResponse> {
  return apiRequest<OrderResponse>("PUT", `/orders/${id}/status/${status}`);
}

export async function cancelOrder(id: number): Promise<OrderResponse> {
  return apiRequest<OrderResponse>("POST", `/orders/${id}/cancel`);
}
