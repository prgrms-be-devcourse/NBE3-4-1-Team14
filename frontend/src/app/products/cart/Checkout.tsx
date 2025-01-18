"use client";
import { CheckoutButtonProps } from "../../types";
import { useOrder } from "../../hooks/useOrder";
import { useState } from "react";

export function CheckoutButton({ products }: CheckoutButtonProps) {
  const { handleCheckout } = useOrder();
  const [email, setEmail] = useState("");
  const [address, setAddress] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("제출된 폼 데이터:", {
      email: email,
      address: address,
    });

    if (!email || !address) {
      alert("이메일과 주소를 모두 입력해주세요.");
      return;
    }
    handleCheckout(products, email, address);
  };

  return (
    <form onSubmit={handleSubmit} className="w-full space-y-4">
      <div>
        <label
          htmlFor="email"
          className="block text-sm font-medium text-gray-700 mb-1"
        >
          이메일
        </label>
        <input
          type="email"
          id="email"
          required
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="이메일을 입력하세요"
          className="w-full text-gray-700 px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-gray-500"
        />
      </div>

      <div>
        <label
          htmlFor="address"
          className="block text-sm font-medium text-gray-700 mb-1"
        >
          배송 주소
        </label>
        <input
          type="text"
          id="address"
          required
          value={address}
          onChange={(e) => setAddress(e.target.value)}
          placeholder="배송 받으실 주소를 입력하세요"
          className="w-full text-gray-700 px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-gray-500"
        />
      </div>

      <button
        type="submit"
        className="w-full bg-gray-900 text-white text-sm font-medium h-10 rounded-lg hover:bg-gray-800 transition-colors duration-200 flex items-center justify-center gap-2"
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
    </form>
  );
}
