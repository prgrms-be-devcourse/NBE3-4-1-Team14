"use client";

import OrderItem from "@/app/orders/OrderItem";
import { Order } from "@/app/types";
import { useState } from "react";

interface OrderResponse {
  statusCode: number;
  message: string;
  data: Order[];
}

export default function ClientPage() {
  const [orders, setOrders] = useState<Order[]>([]);
  const [email, setEmail] = useState<string>("");

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault(); // 폼 기본 동작 방지
    const response = await fetch("http://localhost:7070/api/v1/order/" + email);
    if (!response.ok) {
      console.log(response.status);
      return;
    }

    const responseBody: OrderResponse = await response.json();
    console.log("서버 응답:", responseBody); // 응답 확인을 위한 로그 추가

    if (responseBody.statusCode != 200) {
      if (responseBody.statusCode == 404) {
        alert("입력한 이메일에 대한 주문 내역이 없습니다.");
      } else {
        alert("서버에 요청이 잘못되었습니다.");
      }
      return;
    }

    setOrders(responseBody.data);
  };

  return (
    <div className="max-w-6xl mx-auto px-4 py-6 bg-white">
      <header className="mb-6">
        <h1 className="text-2xl font-bold text-gray-900">주문 내역</h1>
        <p className="text-sm text-gray-600 mt-1">
          주문하신 상품의 내역을 확인하세요
        </p>
      </header>

      <form onSubmit={handleSubmit} className="mb-8">
        <div className="flex gap-4">
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="이메일을 입력하세요"
            className="flex-1 px-4 py-2 border border-gray-300 rounded-lg text-gray-900 focus:outline-none focus:ring-2 focus:ring-gray-600"
            required
          />
          <button
            type="submit"
            className="px-6 py-2 bg-gray-900 text-white text-white rounded-lg hover:bg-gray-600 transition-colors"
          >
            주문 조회
          </button>
        </div>
      </form>

      {orders.length === 0 ? (
        <div className="text-center py-12 text-gray-500">
          주문 내역이 없거나 이메일을 입력해주세요.
        </div>
      ) : (
        <div className="space-y-6">
          {orders.map((order) => (
            <OrderItem key={order.orderId} order={order} />
          ))}
        </div>
      )}
    </div>
  );
}
