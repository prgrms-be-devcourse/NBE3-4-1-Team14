"use client";
import { CartItemProps } from "../../types";

export function CartItem({ product, count }: CartItemProps) {
  const formatPrice = (price: number) => {
    return new Intl.NumberFormat("ko-KR").format(price);
  };

  return (
    <div className="flex justify-between items-center py-2 border-b">
      <div>
        <h3 className="font-medium">{product.name}</h3>
        <p className="text-gray-600">개수: {count}개</p>
      </div>
      <p className="font-semibold">{formatPrice(product.price * count)}원</p>
    </div>
  );
}
