import React from "react";
import { OrderDetail } from "../types/board";

interface OrderListProps {
    orders: OrderDetail[];
    onOrderClick: (order: OrderDetail) => void; // 주문 상세 정보를 모달로 표시하기 위한 클릭 핸들러
}

const OrderList: React.FC<OrderListProps> = ({ orders, onOrderClick }) => {
    return (
        <div className="bg-white rounded-xl shadow-md border border-gray-200 p-4">
            <div className="overflow-x-auto">
                <table className=" text-sm text-left text-gray-500">
                    <thead className="text-xs text-gray-700 uppercase bg-gray-100 border-b">
                    <tr>
                        <th className="px-6 py-3 text-center">주문 날짜</th>
                        <th className="px-6 py-3 text-center">주문 번호</th>
                        <th className="px-6 py-3 text-center">주문 품목</th>
                        <th className="px-6 py-3 text-center">주문 총액</th>
                        <th className="px-6 py-3 text-center">주문 상태</th>
                        <th className="px-6 py-3 text-center">이메일</th>
                        <th className="px-6 py-3 text-center">주소</th>
                    </tr>
                    </thead>
                    <tbody>
                    {orders.map((order, index) => (
                        <tr
                            key={order.id}
                            className={`hover:bg-gray-50 transition-colors cursor-pointer ${
                                index % 2 === 0 ? "bg-white" : "bg-gray-50"
                            }`}
                            onClick={() => onOrderClick(order)}
                        >
                            <td className="px-6 py-4 text-center text-gray-900">
                                {new Date(order.orderDate).toLocaleString()} {/* 날짜 포맷 */}
                            </td>
                            <td className="px-6 py-4 text-center text-gray-900">
                                {order.uuid}
                            </td>
                            <td className="px-6 py-4 text-center text-gray-500">
                                {order.items.map((item) => (
                                    <span key={item.name} className="block">
                                            {item.name} ({item.quantity}개)
                                        </span>
                                ))}
                            </td>
                            <td className="px-6 py-4 text-right text-gray-900 font-semibold">
                                ₩{order.totalPrice.toLocaleString()} {/* 총액 표시 */}
                            </td>
                            <td className="px-6 py-4 text-center text-gray-500">
                                {order.orderStatus === "WAITING" && (
                                    <span className="text-blue-500 font-semibold">
                                            배송준비중
                                        </span>
                                )}
                                {order.orderStatus === "COMPLETED" && (
                                    <span className="text-green-500 font-semibold">
                                            배송완료
                                        </span>
                                )}
                                {order.orderStatus === "CANCELED" && (
                                    <span className="text-red-500 font-semibold">
                                            주문취소
                                        </span>
                                )}
                            </td>
                            <td className="px-6 py-4 text-center text-gray-900">
                                {order.email}
                            </td>
                            <td className="px-6 py-4 text-center text-gray-900">
                                {order.address}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default OrderList;
