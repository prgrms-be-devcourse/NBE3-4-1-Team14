"use client";

import { CheckoutButton } from "./Checkout";
import { CartProps } from "../../types";
import { useCart } from "../../hooks/useCart";
import { useFormatPrice } from "../../hooks/useFormatPrice";

export function Cart({ products }: CartProps) {
  const { cartCounts, setCartCounts } = useCart();
  const formatPrice = useFormatPrice();

  const totalAmount = Object.entries(cartCounts).reduce(
    (total, [id, count]) => {
      const product = products.find((p) => p.id === Number(id));
      return total + (product?.price || 0) * count;
    },
    0
  );

  // 장바구니에서 상품 제거하는 함수
  const handleRemoveItem = (productId: number) => {
    setCartCounts((prevCounts) => {
      const newCounts = { ...prevCounts };
      delete newCounts[productId];
      return newCounts;
    });
  };

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
                  <div className="flex items-center gap-4">
                    <p className="text-gray-900 font-medium">
                      {formatPrice(product.price * count)}원
                    </p>
                    <button
                      onClick={() => handleRemoveItem(product.id)}
                      className="text-red-500 hover:text-red-700 transition-colors p-1"
                      aria-label="상품 제거"
                    >
                      <svg
                        className="w-5 h-5"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth="2"
                          d="M6 18L18 6M6 6l12 12"
                        />
                      </svg>
                    </button>
                  </div>
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
