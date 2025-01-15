"use client";
import { Order } from "../types";

interface OrderItemProps {
  order: Order;
}

export default function OrderItem({ order }: OrderItemProps) {
  const formatPrice = (price: number) => {
    return new Intl.NumberFormat("ko-KR").format(price);
  };

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString("ko-KR", {
      year: "numeric",
      month: "long",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  return (
    <div className="border border-gray-100 rounded-lg shadow-sm">
      <div className="px-4 py-3 border-b border-gray-100">
        <div className="flex justify-between items-center">
          <p className="text-sm text-gray-600">
            주문일시: {formatDate(order.orderDate)}
          </p>
          <p className="text-sm font-medium text-gray-900">
            주문번호: {order.orderId}
          </p>
        </div>
      </div>

      <div className="p-4">
        <div className="space-y-3">
          {order.items.map((item) => (
            <div
              key={item.productId}
              className="flex justify-between items-center text-sm"
            >
              <div>
                <p className="text-gray-900 font-medium">{item.productName}</p>
                <p className="text-gray-600">
                  {formatPrice(item.price)}원 × {item.quantity}개
                </p>
              </div>
              <p className="text-gray-900">{formatPrice(item.totalPrice)}원</p>
            </div>
          ))}
        </div>

        <div className="mt-4 pt-3 border-t border-gray-100">
          <div className="flex justify-between items-center">
            <p className="text-sm font-medium text-gray-900">총 결제금액</p>
            <p className="text-lg font-bold text-gray-900">
              {formatPrice(order.totalAmount)}원
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
