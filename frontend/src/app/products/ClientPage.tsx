"use client";
import React, { useEffect } from "react";
import { ClientPageProps } from "../types";
import ProductList from "./productList/ProductList";
import { Cart } from "./cart/Cart";
import { useProductStore } from "../store/useProductStore";

export default function ClientPage({ products }: ClientPageProps) {
  // products를 전역 상태에서 가져오기
  const setProducts = useProductStore((state) => state.setProducts);

  // 컴포넌트가 마운트될 때 store 초기화
  useEffect(() => {
    setProducts(products);
  }, [products, setProducts]);

  return (
    <div className="max-w-6xl mx-auto px-4 py-6">
      <header className="mb-6">
        <h1 className="text-2xl font-bold text-gray-900">상품 조회</h1>
        <p className="text-sm text-gray-600 mt-1">다양한 상품을 둘러보세요</p>
      </header>

      <div className="flex flex-col lg:flex-row gap-6">
        <div className="lg:flex-1">
          <ProductList products={products} />
        </div>
        <div className="lg:w-80">
          <Cart products={products} />
        </div>
      </div>
    </div>
  );
}
