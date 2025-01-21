import React, { useState, useEffect } from "react";
import OrderList from "./OrderList";
import OrderModal from "./OrderModal";
import { OrderDetail } from "../types/board";
import { getOrderList } from "./orderAPI";

const OrderSection: React.FC = () => {
    const [orders, setOrders] = useState<OrderDetail[]>([]); // 주문 데이터 상태
    const [loading, setLoading] = useState<boolean>(true); // 로딩 상태
    const [error, setError] = useState<string | null>(null); // 에러 상태
    const [selectedOrder, setSelectedOrder] = useState<OrderDetail | null>(null); // 선택된 주문 상세 정보

    // 주문 목록 불러오기
    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const data = await getOrderList(); // API 호출로 주문 목록 가져오기
                setOrders(data);
            } catch (err: any) {
                setError(err.message || "데이터를 불러오는 중 오류가 발생했습니다.");
            } finally {
                setLoading(false); // 로딩 상태 해제
            }
        };

        fetchOrders(); // 데이터 로드
    }, []);

    const handleOrderClick = (order: OrderDetail) => {
        setSelectedOrder(order); // 선택한 주문을 모달로 표시
    };

    const closeModal = () => {
        setSelectedOrder(null); // 모달 닫기
    };

    if (loading) {
        return <p className="text-center text-gray-500">로딩 중...</p>;
    }

    if (error) {
        return <p className="text-center text-red-500">오류가 발생했습니다: {error}</p>;
    }

    return (
        <div
            className="max-w-screen-2xl bg-gray-50 p-4"
            style={{ display: "flex", flexDirection: "column" }}
        >
            <div className="flex-grow overflow-y-auto">
                <OrderList orders={orders} onOrderClick={handleOrderClick} />
            </div>
            {selectedOrder && <OrderModal order={selectedOrder} onClose={closeModal} />}
        </div>
    );
};
export default OrderSection;