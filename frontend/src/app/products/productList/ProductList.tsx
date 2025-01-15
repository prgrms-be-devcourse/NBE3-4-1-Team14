"use client";

import React from "react";
import { Product, ProductListProps } from "../../types";
import { useCart } from "../../hooks/useCart";
import ProductItem from "./Product";

export default function ProductList({ products }: ProductListProps) {
  const { selectedCounts, setSelectedCounts, setCartCounts } = useCart();

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

  return (
    <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
      {products.map((product) => (
        <ProductItem
          key={product.id}
          product={product}
          onAddToCart={handleAddToCart}
        />
      ))}
    </div>
  );
}
