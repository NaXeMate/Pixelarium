import type { Category } from "./commonTypes";

export type ProductResponse = {
  id: number;
  name: string;
  description: string;
  price: number;
  salePrice?: number;
  imagePath?: string;
  stock: number;
  category: Category;
};

export type CreateProduct = {
  name: string;
  description: string;
  price: number;
  salePrice?: number;
  imagePath?: string;
  stock: number;
  category: Category;
};
