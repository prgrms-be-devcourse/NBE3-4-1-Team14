import React from "react";
import { OrderDetail } from "../types/board";

interface OrderHandlersProps {
    orders: OrderDetail[];
}

const OrderHandlers: React.FC<OrderHandlersProps> = ({ orders }) => {
    // 주문 상태별로 필터링된 데이터 개수 계산
    const orderStatusCount = {
        WAITING: orders.filter(order => order.orderStatus === "WAITING").length,
        COMPLETED: orders.filter(order => order.orderStatus === "COMPLETED").length,
        CANCELED: orders.filter(order => order.orderStatus === "CANCELED").length,
    };

    return (
        <div className="flex flex-col md:flex-row justify-between items-center mb-6">
            <h2 className="text-lg font-bold">주문 조회</h2>
            <div className="flex space-x-4 mt-4 md:mt-0">
                <div className="flex flex-col items-center">
                    <span className="font-medium">대기 중</span>
                    <span className="text-blue-500">{orderStatusCount.WAITING}</span>
                </div>
                <div className="flex flex-col items-center">
                    <span className="font-medium">완료</span>
                    <span className="text-green-500">{orderStatusCount.COMPLETED}</span>
                </div>
                <div className="flex flex-col items-center">
                    <span className="font-medium">취소됨</span>
                    <span className="text-red-500">{orderStatusCount.CANCELED}</span>
                </div>
            </div>
        </div>
    );
};

export default OrderHandlers;
