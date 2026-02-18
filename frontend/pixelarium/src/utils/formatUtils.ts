/**
 * Formats a number as a price string in Euros with proper European formatting.
 * Example: 19.99 -> "19,99 €"
 */
export const formatPrice = (price: number): string => {
  return price.toFixed(2).replace(".", ",") + " €";
};
