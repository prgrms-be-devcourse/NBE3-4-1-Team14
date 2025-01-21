"use client";

import React, { useEffect, useState } from "react";

import ProductHandlers from "./ProductList";
import { ProductDetail } from "../types/board";
import { getProductList } from "./productAPI";

export default function AdminProductSection() {
    const [products, setProducts] = useState<ProductDetail[]>([]);
    const [selectedProductId, setSelectedProductId] = useState<number | null>(null);
    const [error, setError] = useState("");

    // 상품 목록 가져오기
    useEffect(() => {
        (async () => {
            try {
                const productList = await getProductList();
                setProducts(productList);
            } catch (err) {
                console.error("상품 목록 가져오기 에러:", err);
                if (err instanceof Error) {
                    setError("상품 정보를 가져오지 못했습니다. 잠시 후 다시 시도해주세요.");
                } else {
                    setError("상품 목록 가져오는 중 예기치 못한 문제가 발생했습니다.");
                }
            }
        })();
    }, []);

    return (
        <div className="mt-6 bg-gray-50">
            {/* 메인 컨텐츠 */}
            <main className="pb-8 px-4 sm:px-6 lg:px-8 max-w-7xl mx-auto">
                {/* 오류 메시지 */}
                {error && (
                    <div className="mb-6 p-4 bg-red-50 rounded-lg border border-red-100">
                        <p className="text-sm text-red-600">{error}</p>
                    </div>
                )}

                {/* 핸들러 */}
                <div className="mb-2">
                    <ProductHandlers
                        selectedProductId={selectedProductId}
                        setSelectedProductId={setSelectedProductId}
                        products={products}
                        setProducts={setProducts}
                        setError={setError}
                    />
                </div>

                {/* 상품 테이블 */}
                <div className="bg-white rounded-xl shadow-sm border border-gray-100">
                    <div className="overflow-auto max-h-[780px]">
                        <table className="w-full min-w-[100px]">
                            <thead>
                            <tr className="border-b border-gray-100">
                                <th className="px-1 py-4 text-center text-l font-bold text-gray-500 uppercase tracking-wider">선택</th>
                                <th className="px-6 py-4 text-center text-l font-bold text-gray-500 uppercase tracking-wider">상품번호</th>
                                <th className="px-6 py-4 text-center text-l font-bold text-gray-500 uppercase tracking-wider">상품명</th>
                                <th className="px-6 py-4 text-center text-l font-bold text-gray-500 uppercase tracking-wider">설명</th>
                                <th className="px-6 py-4 text-center text-l font-bold text-gray-500 uppercase tracking-wider">단가</th>
                                <th className="px-6 py-4 text-center text-l font-bold text-gray-500 uppercase tracking-wider">재고수량</th>
                                <th className="px-6 py-4 text-center text-l font-bold text-gray-500 uppercase tracking-wider">이미지</th>
                            </tr>
                            </thead>
                            <tbody className="divide-y divide-gray-100">
                            {products.map((product) => (
                                <tr
                                    key={product.id}
                                    className="hover:bg-gray-50 transition-colors"
                                >
                                    <td className="px-1 py-2 whitespace-nowrap text-center">
                                        <input
                                            type="radio"
                                            name="selectedProduct"
                                            onChange={() => setSelectedProductId(product.id)}
                                            checked={selectedProductId === product.id}
                                            className="w-4 h-4 text-blue-600 border-gray-300 focus:ring-blue-500"
                                        />
                                    </td>
                                    <td className="px-6 py-2 whitespace-nowrap text-sm text-gray-900">{product.id}</td>
                                    <td className="px-6 py-2 whitespace-nowrap text-sm font-medium text-gray-900">{product.name}</td>
                                    <td className="px-6 py-2 text-sm text-gray-500 max-w-xs truncate">{product.description}</td>
                                    <td className="px-6 py-2 whitespace-nowrap text-sm text-gray-900 text-right">
                                        ₩{product.price ? product.price.toLocaleString() : "0"}
                                    </td>
                                    <td className="px-6 py-2 whitespace-nowrap text-sm text-gray-900 text-right">
                                        {product.quantity !== undefined ? product.quantity.toLocaleString() : "0"}
                                    </td>
                                    <td className="px-6 py-2 whitespace-nowrap text-center">
                                        <div className="flex flex-col items-center">
                                            {/*<span*/}
                                            {/*    className="text-sm text-gray-700">{product.filename || "No filename available"}*/}
                                            {/*</span>*/}
                                            <img
                                                src={`http://localhost:8080/api/v1/image/path/${product.filename}`}
                                                alt={product.filename}
                                                className="w-16 h-16 rounded-lg object-cover ring-1 ring-gray-200"
                                            />
                                        </div>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </main>
        </div>
    );
}
