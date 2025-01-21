"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";

interface NavigationBarProps {
    onChangePage: (page: string) => void;
}

const NavigationBar: React.FC<NavigationBarProps> = ({ onChangePage }) => {
    const [currentPage, setCurrentPage] = useState("product");

    const handlePageChange = (page: string) => {
        setCurrentPage(page);
        onChangePage(page);
    };

    const handleLogout = () => {
        // 토큰 삭제
        localStorage.removeItem("authToken");

        // 알림 메시지
        alert("로그아웃 성공!");

        // SPA 방식으로 리다이렉트
        const router = useRouter();
        router.push("/admin");
    };

    return (
        <header className="bg-white shadow-sm fixed top-0 left-0 right-0 z-10">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex justify-between items-center h-16">
                    {/* 타이틀 */}
                    <div className="flex items-center space-x-6">
                        <h1 className="text-lg font-semibold text-gray-900">
                            GC Coffeeㅣ관리자 페이지
                        </h1>
                        {/* 네비게이션 버튼 */}
                        <div className="flex space-x-4">
                            <button
                                onClick={() => handlePageChange("product")}
                                className={`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
                                    currentPage === "product"
                                        ? "bg-indigo-600 text-white"
                                        : "bg-gray-100 text-gray-500 hover:bg-gray-200"
                                }`}
                            >
                                상품 관리
                            </button>
                            <button
                                onClick={() => handlePageChange("order")}
                                className={`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
                                    currentPage === "order"
                                        ? "bg-indigo-600 text-white"
                                        : "bg-gray-100 text-gray-500 hover:bg-gray-200"
                                }`}
                            >
                                주문 관리
                            </button>
                        </div>
                    </div>
                    {/* 로그아웃 버튼 */}
                    <button
                        onClick={handleLogout}
                        className="inline-flex items-center px-4 py-2 rounded-lg text-sm font-medium text-gray-500 bg-gray-100 hover:bg-gray-200 transition-colors"
                    >
                        로그아웃
                    </button>
                </div>
            </div>
        </header>
    );
};

export default NavigationBar;
