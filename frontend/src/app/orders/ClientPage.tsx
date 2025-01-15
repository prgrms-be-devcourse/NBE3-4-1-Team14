"use client";
import { useEffect, useState } from "react";
import { Order } from "../types";
import OrderItem from "./OrderItem";

export default function ClientPage() {
  const [orders, setOrders] = useState<Order[]>([]);

  useEffect(() => {
    const savedOrders = localStorage.getItem("orders");
    if (savedOrders) {
      setOrders(JSON.parse(savedOrders));
    }
  }, []);

  if (orders.length === 0) {
    return (
      <div className="max-w-6xl mx-auto px-4 py-6 bg-white">
        <header className="mb-6">
          <h1 className="text-2xl font-bold text-gray-900">주문 내역</h1>
          <p className="text-sm text-gray-600 mt-1">
            주문하신 상품의 내역을 확인하세요
          </p>
        </header>
        <div className="text-center py-12 text-gray-500">
          아직 주문 내역이 없습니다.
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-6xl mx-auto px-4 py-6 bg-white">
      <header className="mb-6">
        <h1 className="text-2xl font-bold text-gray-900">주문 내역</h1>
        <p className="text-sm text-gray-600 mt-1">
          주문하신 상품의 내역을 확인하세요
        </p>
      </header>

      <div className="space-y-6">
        {orders.map((order) => (
          <OrderItem key={order.orderId} order={order} />
        ))}
      </div>
    </div>
  );
}
