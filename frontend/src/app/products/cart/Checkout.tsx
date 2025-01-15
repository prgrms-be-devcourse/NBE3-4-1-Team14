"use client";
import { CheckoutButtonProps } from "../../types";
import { useOrder } from "../../hooks/useOrder";

export function CheckoutButton({ products }: CheckoutButtonProps) {
  const { handleCheckout } = useOrder();

  return (
    <button
      onClick={() => handleCheckout(products)}
      className="w-full mt-4 bg-gray-900 text-white text-sm font-medium h-10 rounded-lg hover:bg-gray-800 transition-colors duration-200 flex items-center justify-center gap-2"
    >
      <svg
        className="w-4 h-4"
        fill="none"
        stroke="currentColor"
        viewBox="0 0 24 24"
      >
        <path
          strokeLinecap="round"
          strokeLinejoin="round"
          strokeWidth="2"
          d="M5 13l4 4L19 7"
        />
      </svg>
      결제하기
    </button>
  );
}
