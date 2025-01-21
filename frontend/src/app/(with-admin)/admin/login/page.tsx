"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

export default function LoginPage() {
    const [adminId, setAdminId] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false); // 서버 요청 대기 상태
    const router = useRouter();

    // 서버 응답 상태 코드에 따른 에러 처리, 관리자 페이지에서만 사용
    function handleErrorResponse(response: Response) {
        if (!response.ok) {
            switch (response.status) {
                case 401:
                    throw new Error("인증 실패: 아이디 또는 비밀번호가 잘못되었습니다.");
                case 403:
                    throw new Error("접근 권한이 없습니다.");
                case 500:
                    throw new Error("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
                default:
                    throw new Error(`알 수 없는 오류가 발생했습니다. 상태 코드: ${response.status}`);
            }
        }
    }

    // 로그인 요청 핸들러
    async function handleLogin(event: React.FormEvent) {
        event.preventDefault(); // 폼 기본 동작 중단
        setLoading(true); // 로딩 상태 시작
        setError(""); // 기존 에러 초기화

        try {
            const response = await fetch("http://localhost:8080/api/v1/admin/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                // JWT를 쿠키에 저장하고 요청마다 포함하도록 설정
                credentials: "include", // 쿠키 전송 설정
                body: JSON.stringify({ username: adminId, password }), // JSON 형태로 요청 데이터 전송
            });

            handleErrorResponse(response); // 응답 상태 코드에 따른 에러 처리

            const data = await response.json(); // JSON 형태로 응답 데이터 파싱

            router.push("/admin/board"); // 인증 성공 후 관리자 페이지로 이동

        } catch (err: any) {
            setError(err.message); // 에러 메시지 표시
            console.error(`Login error: ${err.message}`); // 디버깅용 로깅

        } finally {
            setLoading(false); // 로딩 상태 종료

        }
    }

    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100">
            <div className="w-full max-w-md p-8 bg-white rounded-lg shadow-lg">
                <h1 className="text-2xl font-bold text-gray-800 text-center mb-6">GC Coffee 관리자 로그인</h1>
                <form onSubmit={handleLogin}>
                    <div className="mb-4">
                        <label className="block text-sm font-medium text-gray-700 mb-2"></label>
                        <input
                            type="text"
                            placeholder="아이디"
                            value={adminId}
                            onChange={(e) => setAdminId(e.target.value)}
                            required
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-indigo-500 focus:border-indigo-500"
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-sm font-medium text-gray-700 mb-2"></label>
                        <input
                            type="password"
                            placeholder="비밀번호"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-indigo-500 focus:border-indigo-500"
                        />
                    </div>
                    {error && !loading && (
                        <p className="mb-4 text-sm text-red-500">{error}</p>
                    )}
                    <button
                        type="submit"
                        disabled={loading}
                        className={`w-full py-2 text-white rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-1 ${
                            loading
                                ? "bg-gray-400 cursor-not-allowed"
                                : "bg-indigo-500 hover:bg-indigo-600"
                        }`}
                    >
                        {loading ? "로그인 중..." : "로그인"}
                    </button>
                </form>
            </div>
        </div>
    );
}
