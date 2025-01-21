"use client";

import { Product, ProductListProps } from "../../types";
import { useCart } from "../../hooks/useCart";
import ProductItem from "./Product";
import { useProductStore } from "../../store/useProductStore";

export default function ProductList({ products }: ProductListProps) {
  const { selectedCounts, setSelectedCounts, setCartCounts } = useCart();
  // store에서 products를 가져옴
  const storeProducts = useProductStore((state) => state.products);

  // props의 products나 store의 products 사용
  const displayProducts = products.length > 0 ? products : storeProducts;

  const handleAddToCart = (product: Product) => {
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
  };

  // Blob URL 정리 useEffect 제거 - base64를 사용하므로 더 이상 필요하지 않음

  return (
    <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
      {displayProducts.map((product: Product) => (
        <ProductItem
          key={product.id}
          product={product}
          onAddToCart={handleAddToCart}
        />
      ))}
    </div>
  );
}
