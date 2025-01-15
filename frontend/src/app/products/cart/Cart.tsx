"use client";

import { CheckoutButton } from "./Checkout";
import { CartProps } from "../../types";
import { useCart } from "../../hooks/useCart";
import { useFormatPrice } from "../../hooks/useFormatPrice";

export function Cart({ products }: CartProps) {
  const { cartCounts } = useCart();
  const formatPrice = useFormatPrice();

  const totalAmount = Object.entries(cartCounts).reduce(
    (total, [id, count]) => {
      const product = products.find((p) => p.id === Number(id));
      return total + (product?.price || 0) * count;
    },
    0
  );

  return (
    <div className="bg-white border border-gray-100 rounded-lg shadow-sm sticky top-4">
      <div className="p-4 border-b border-gray-100">
        <h2 className="text-lg font-medium text-gray-900">장바구니</h2>
        <p className="text-sm text-gray-500 mt-1">
          {Object.keys(cartCounts).length}개의 상품
        </p>
      </div>

      <div className="p-4">
        {Object.keys(cartCounts).length === 0 ? (
          <p className="text-sm text-gray-500 text-center py-8">
            장바구니가 비어있습니다
          </p>
        ) : (
          <div className="space-y-3">
            {Object.entries(cartCounts).map(([id, count]) => {
              const product = products.find((p) => p.id === Number(id));
              if (!product) return null;

              return (
                <div
                  key={id}
                  className="flex items-center justify-between text-sm"
                >
                  <div className="flex-1 min-w-0">
                    <h3 className="text-gray-900 truncate">{product.name}</h3>
                    <p className="text-gray-500">수량: {count}개</p>
                  </div>
                  <p className="ml-4 text-gray-900 font-medium">
                    {formatPrice(product.price * count)}원
                  </p>
                </div>
              );
            })}

            <div className="pt-4 border-t border-gray-100 mt-4">
              <div className="flex justify-between text-sm">
                <span className="font-medium text-gray-900">총 결제금액</span>
                <span className="font-bold text-blue-600">
                  {formatPrice(totalAmount)}원
                </span>
              </div>
              <CheckoutButton products={products} />
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
