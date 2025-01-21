import React from "react";
import { OrderDetail } from "../types/board";

interface OrderModalProps {
  order: OrderDetail;
  onClose: () => void;
}

const OrderModal: React.FC<OrderModalProps> = ({ order, onClose }) => {
  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
      <div className="bg-white text-gray-900 rounded-lg shadow-lg p-8 max-w-md w-full">
        <h2 className="text-2xl text-gray-900 font-bold mb-4">
          주문 상세 정보
        </h2>
        <p>
          <strong>주문 번호:</strong> {order.uuid}
        </p>
        <p>
          <strong>주문 날짜:</strong> {order.orderDate}
        </p>
        <p>
          <strong>품목:</strong>
        </p>
        <ul className="list-disc list-inside ml-4 mb-4">
          {order.items.map((item, index) => (
            <li key={index}>
              {item.name} - {item.quantity}개 (₩{item.price.toLocaleString()})
            </li>
          ))}
        </ul>
        <p>
          <strong>총 금액:</strong> ₩{order.totalPrice.toLocaleString()}
        </p>
        <p>
          <strong>상태:</strong> {order.orderStatus}
        </p>
        <p>
          <strong>이메일:</strong> {order.email}
        </p>
        <p>
          <strong>주소:</strong> {order.address}
        </p>
        <button
          onClick={onClose}
          className="mt-4 bg-indigo-600 text-white px-4 py-2 rounded-lg hover:bg-indigo-700"
          aria-label="닫기"
        >
          닫기
        </button>
      </div>
    </div>
  );
};

export default OrderModal;
