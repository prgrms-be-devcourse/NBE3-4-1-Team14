// ./products/[id]/page.tsx
"use client";

import { useRouter } from "next/navigation";
import { useCart } from "../../hooks/useCart";
import { useFormatPrice } from "../../hooks/useFormatPrice";
import { use } from "react";

interface SearchParams {
  name: string;
  price: string;
  description: string;
}

export default function ProductDetailPage({
  params,
  searchParams,
}: {
  params: Promise<{ id: string }>;
  searchParams: Promise<SearchParams>;
}) {
  const resolvedParams = use(params);
  const resolvedSearchParams = use(searchParams);

  const router = useRouter();
  const { selectedCounts, setSelectedCounts, setCartCounts } = useCart();
  const formatPrice = useFormatPrice();

  const product = {
    id: Number(resolvedParams.id),
    name: resolvedSearchParams.name,
    price: Number(resolvedSearchParams.price),
    description: resolvedSearchParams.description,
  };

  const selectedCount = selectedCounts[product.id] || 0;

  const handleCountChange = (count: number) => {
    if (count >= 0) {
      setSelectedCounts((prevCounts) => ({
        ...prevCounts,
        [product.id]: count,
      }));
    }
  };

  const handleAddToCart = () => {
    const amount = selectedCounts[product.id] || 0;
    if (amount === 0) {
      alert("수량을 선택해주세요");
      return;
    }

    setCartCounts((prevCounts) => {
      const currentCount = prevCounts[product.id] || 0;
      return {
        ...prevCounts,
        [product.id]: currentCount + amount,
      };
    });
    setSelectedCounts((prevCounts) => ({ ...prevCounts, [product.id]: 0 }));
    router.back();
  };

  return (
    <div className="max-w-6xl mx-auto px-4 py-6 bg-white">
      <button
        onClick={() => router.back()}
        className="flex items-center text-gray-600 hover:text-gray-900 mb-6 group"
      >
        <svg
          className="w-5 h-5 mr-2 transform group-hover:-translate-x-1 transition-transform"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="M15 19l-7-7 7-7"
          />
        </svg>
        뒤로 가기
      </button>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div className="bg-gray-200 aspect-square flex items-center justify-center text-gray-500">
          상품 이미지
        </div>

        <div className="space-y-6">
          <div>
            <h1 className="text-3xl font-bold text-gray-900">{product.name}</h1>
            <p className="text-2xl font-bold text-gray-900 mt-2">
              {formatPrice(product.price)}원
            </p>
          </div>

          <div>
            <h2 className="text-lg font-medium text-gray-900 mb-2">
              상품 설명
            </h2>
            <p className="text-gray-600 whitespace-pre-line">
              {product.description}
            </p>
          </div>

          <div className="pt-6 border-t border-gray-200">
            <div className="flex items-center gap-4">
              <div className="relative">
                <input
                  type="number"
                  min="0"
                  value={selectedCount}
                  onChange={(e) => handleCountChange(Number(e.target.value))}
                  className="w-20 h-10 pl-3 pr-2 border border-gray-900 rounded text-center text-sm focus:outline-none focus:ring-1 focus:ring-gray-900 text-gray-900"
                />
              </div>
              <button
                onClick={handleAddToCart}
                className="flex-1 h-10 bg-gray-900 text-white rounded font-medium hover:opacity-90 transition-opacity duration-200 flex items-center justify-center gap-2"
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
                    d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z"
                  />
                </svg>
                장바구니에 담기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
