import { OrderDetail } from "../types/board";

const API_BASE_URL = "http://localhost:8080/api/v1/order";

// 주문 목록 조회 API
export const getOrderList = async (): Promise<OrderDetail[]> => {
    try {
        const response = await fetch(`${API_BASE_URL}/list`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });

        // 응답 상태 코드 확인
        if (!response.ok) {
            const errorMessage = `서버 요청 중 오류가 발생했습니다. 상태 코드: ${response.status} - ${response.statusText}`;
            console.error(errorMessage);
            throw new Error(errorMessage);
        }

        const data = await response.json();

        // 데이터 유효성 검사
        if (!data || typeof data !== "object" || !Array.isArray(data.data)) {
            const errorMessage = "API에서 반환된 데이터 형식이 올바르지 않습니다.";
            console.error(errorMessage, data);
            throw new Error(errorMessage);
        }

        // 데이터 반환
        return data.data as OrderDetail[];
    } catch (error: any) {
        console.error("주문 목록을 가져오는 중 오류 발생:", error.message || error);
        throw error; // Error 객체를 그대로 던져 호출자가 더 처리할 수 있게 함
    }
};
