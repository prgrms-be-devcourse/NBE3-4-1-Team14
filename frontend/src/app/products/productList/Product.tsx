"use client";

import { ProductItemProps } from "../../types";
import { useCart } from "../../hooks/useCart";
import { useFormatPrice } from "../../hooks/useFormatPrice";
import { useRouter } from "next/navigation";

export default function ProductItem({
  product,
  onAddToCart,
}: ProductItemProps) {
  const router = useRouter();
  const { selectedCounts, setSelectedCounts } = useCart();
  const formatPrice = useFormatPrice();

  const handleCountChange = (count: number) => {
    if (count >= 0) {
      setSelectedCounts((prevCounts) => ({
        ...prevCounts,
        [product.id]: count,
      }));
    }
  };

  const selectedCount = selectedCounts[product.id] || 0;

  const handleProductClick = () => {
    // 상품 정보를 URL 파라미터로 전달
    router.push(
      `/products/${product.id}?name=${encodeURIComponent(product.name)}&price=${
        product.price
      }&description=${encodeURIComponent(product.description)}`
    );
  };

  return (
    <div className="relative overflow-hidden bg-white rounded-lg border border-gray-100">
      <div className="cursor-pointer" onClick={handleProductClick}>
        <div className="h-32 bg-gray-200 w-full">
          <div className="w-full h-full flex items-center justify-center text-gray-500 text-sm">
            상품 이미지
          </div>
        </div>

        <div className="p-3">
          <div className="mb-2">
            <h3 className="font-medium text-base text-gray-900">
              {product.name}
            </h3>
            <p className="font-semibold text-gray-900">
              {formatPrice(product.price)}원
            </p>
            <p className="text-sm text-gray-500 mt-1 line-clamp-2">
              {product.description}
            </p>
          </div>
        </div>
      </div>

      <div className="p-3 pt-0">
        <div className="flex gap-2">
          <div className="relative">
            <input
              type="number"
              min="0"
              value={selectedCount}
              onChange={(e) => handleCountChange(Number(e.target.value))}
              className="w-14 h-8 pl-2 pr-1 border border-gray-900 rounded text-center text-sm focus:outline-none focus:ring-1 focus:ring-gray-900 text-gray-900"
            />
          </div>
          <button
            onClick={(e) => {
              e.stopPropagation();
              onAddToCart(product);
            }}
            className="flex-1 h-8 bg-gray-900 text-white text-sm rounded font-medium hover:opacity-90 transition-opacity duration-200 flex items-center justify-center gap-1"
          >
            <svg
              className="w-3 h-3"
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
            담기
          </button>
        </div>
      </div>
    </div>
  );
}
