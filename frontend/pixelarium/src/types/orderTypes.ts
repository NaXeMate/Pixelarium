import type { Status } from "./commonTypes";
import type { ProductResponse } from "./productTypes";

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

export type CartItem = {
  product: ProductResponse;
  quantity: number;
};
