"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";

interface NavigationBarProps {
    onChangePage: (page: string) => void;
}

const NavigationBar: React.FC<NavigationBarProps> = ({ onChangePage }) => {
    const [currentPage, setCurrentPage] = useState("product");
    const [loading, setLoading] = useState(false); // 서버 요청 대기 상태
    const [error, setError] = useState("");
    const router = useRouter();


    const handlePageChange = (page: string) => {
        setCurrentPage(page);
        onChangePage(page);
    };

    // 로그아웃 요청 핸들러
    async function handleLogout() {
        setLoading(true); // 로딩 상태 시작
        setError(""); // 기존 에러 초기화

        try {
            const response = await fetch("http://localhost:8080/api/v1/admin/logout", {
                method: "POST", // 로그아웃은 POST 요청
                headers: {
                    "Content-Type": "application/json",
                },
                credentials: "include", // 쿠키 전송 설정
            });
            router.push("/admin/login"); // 로그아웃 후 로그인 페이지로 리다이렉트
        } catch (err: any) {
            setError(err.message); // 에러 메시지 표시
            console.error(`Logout error: ${err.message}`); // 디버깅용 로깅
        } finally {
            setLoading(false); // 로딩 상태 종료
        }
    }


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
