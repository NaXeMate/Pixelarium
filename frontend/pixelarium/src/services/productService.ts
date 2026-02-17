import { apiRequest } from "./api";
import type {
  Category,
  CreateProduct,
  ProductResponse,
} from "../types/index.ts";

export async function getAllProducts(): Promise<ProductResponse[]> {
  return apiRequest<ProductResponse[]>("GET", `/products`);
}

export async function getProductById(id: number): Promise<ProductResponse> {
  return apiRequest<ProductResponse>("GET", `/products/${id}`);
}

export async function createProduct(
  productData: CreateProduct,
): Promise<ProductResponse> {
  return apiRequest<ProductResponse>("POST", `/products`, productData);
}

export async function updateProduct(
  id: number,
  productData: CreateProduct,
): Promise<ProductResponse> {
  return apiRequest<ProductResponse>("PUT", `/products/${id}`, productData);
}

export async function deleteProduct(id: number): Promise<void> {
  return apiRequest<void>("DELETE", `/products/${id}`);
}

export async function getProductsByCategory(
  category: Category,
): Promise<ProductResponse[]> {
  return apiRequest<ProductResponse[]>("GET", `/products/category/${category}`);
}

export async function getProductsByPriceRange(
  minPrice: number,
  maxPrice: number,
): Promise<ProductResponse[]> {
  return apiRequest<ProductResponse[]>(
    "GET",
    `/products/price-range?min=${minPrice}&max=${maxPrice}`,
  );
}

export async function getSaleOffers(): Promise<ProductResponse[]> {
  return apiRequest<ProductResponse[]>("GET", `/products/sale-offers`);
}
