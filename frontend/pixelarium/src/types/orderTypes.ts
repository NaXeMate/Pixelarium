import type { Status } from "./commonTypes";

export type OrderResponse = {
  id: number;
  userId: number;
  orderDate: string;
  totalPrice: number;
  status: Status;
  orderItems: OrderItem[];
};

export type CreateOrder = {
  userId: number;
  orderItems: OrderItem[];
};

export type OrderItem = {
  productId: number;
  quantity: number;
  price: number;
};
