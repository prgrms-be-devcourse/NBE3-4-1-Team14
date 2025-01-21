"use client";

import { useState } from "react";
import NavigationBar from "./components/NavigationBar";
import ProductSection from "./product/ProductSection";
import OrderSection from "./order/OrderSection";

export default function BoardPage() {
    const [currentPage, setCurrentPage] = useState("product");

    const renderSection = () => {
        switch (currentPage) {
            case "product":
                return <ProductSection />;
            case "order":
                return <OrderSection />;
            default:
                return <div>잘못된 페이지입니다.</div>;
        }
    };

    return (
        <div className="min-h-screen bg-gray-50">
            {/* 네비게이션 바 */}
            <NavigationBar onChangePage={setCurrentPage} />

            {/* 동적으로 전환되는 섹션 */}
            <main>
                {renderSection()}
            </main>
        </div>
    );
}
