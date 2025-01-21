"use client";

import Link from "next/link";
import React, { useState } from "react";
import { CartState, ClientLayoutProps } from "./types";
import { CartContext } from "./hooks/useCart";

export default function ClientLayout({ children }: ClientLayoutProps) {
  const [cartCounts, setCartCounts] = useState<CartState>({});
  const [selectedCounts, setSelectedCounts] = useState<CartState>({});

  return (
    <CartContext.Provider
      value={{
        cartCounts,
        setCartCounts,
        selectedCounts,
        setSelectedCounts,
      }}
    >
      <div className="flex flex-col min-h-screen">
        <header className="bg-white shadow-sm">
          <nav className="max-w-6xl mx-auto px-4">
            <div className="flex justify-between h-14 items-center">
              <div className="flex space-x-6">
                <Link
                  href="/products"
                  className="text-gray-700 hover:text-gray-900 font-medium text-sm transition-colors duration-200"
                >
                  상품조회
                </Link>
                <Link
                  href="/orders"
                  className="text-gray-700 hover:text-gray-900 font-medium text-sm transition-colors duration-200"
                >
                  주문 내역
                </Link>
              </div>
            </div>
          </nav>
        </header>

        <main className="flex-grow bg-gray-50">{children}</main>

        <footer className="bg-white border-t border-gray-100">
          <div className="max-w-6xl mx-auto py-6 px-4">
            <div className="text-center">
              <p className="text-sm text-gray-600">Created by Team 14</p>
            </div>
          </div>
        </footer>
      </div>
    </CartContext.Provider>
  );
}
