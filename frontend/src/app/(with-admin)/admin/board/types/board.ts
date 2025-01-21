// types/board.ts

// OrderItem 타입 정의
export interface OrderItem {
    name: string;
    quantity: number;
    price: number;
}

// OrderDetail 타입 정의
export interface OrderDetail {
    id: number;
    email: string;
    items: OrderItem[];
    uuid: string;
    orderStatus: "WAITING" | "COMPLETED" | "CANCELED"; // 상태를 문자열 리터럴 타입으로 정의
    address: string;
    totalPrice: number;
    orderDate: string; // ISO 형식 날짜 문자열
}

// API 응답 데이터 타입 정의
export interface OrderApiResponse {
    statusCode: number;
    message: string;
    data: OrderDetail[];
}

// 상품 상세데이터 타입 정의
export interface ProductDetail {
    id: number | null;            // 상품 ID
    name: string;                 // 제품 이름
    description: string;          // 제품 설명
    price: number;                // 가격
    quantity: number;             // 수량
    filename?: string;            // 서버에 저장된 이미지 파일 이름
    image?: File;               // 업로드할 이미지 파일
}
