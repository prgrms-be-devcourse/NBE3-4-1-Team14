"use client";

import OrderItem from "./OrderItem";
import { Order } from "@/app/types";
import { useState } from "react";

interface OrderResponse {
  statusCode: number;
  message: string;
  data: Order;
}

interface OrderSearchRequest {
  pw: number;
  orderUuid: string;
}

interface OrderModifyRequest {
  address: string;
  pw: number;
  orderUuid: string;
}

interface OrderCancelReqeust {
  pw: number;
  orderUuid: string;
}

export default function ClientPage() {
  const [order, setOrder] = useState<Order>();
  const [orderNumber, setOrderNumber] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [showEmailForm, setShowAddressForm] = useState(false);
  const [newAddress, setNewAddress] = useState("");
  const [isUpdating, setIsUpdating] = useState(false);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const request: OrderSearchRequest = {
      pw: parseInt(password),
      orderUuid: orderNumber,
    };

    const response = await fetch(
      `http://localhost:8080/api/v1/order?orderUuid=${orderNumber}&password=${password}`,
      { method: "GET", headers: { "Content-Type": "application/json" } }
    );

    if (!response.ok) {
      console.log(response.status);
      const responseBody: OrderResponse = await response.json();
      console.log(responseBody.message);
      return;
    }

    const responseBody: OrderResponse = await response.json();
    console.log("서버 응답:", responseBody);

    if (responseBody.statusCode != 200) {
      if (responseBody.statusCode == 404) {
        alert("입력한 이메일에 대한 주문 내역이 없습니다.");
      } else {
        alert("서버에 요청이 잘못되었습니다.");
      }
      return;
    }

    setOrder(responseBody.data);
  };

  const handleAddressSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newAddress || isUpdating || !order) return;

    setIsUpdating(true);

    const request: OrderModifyRequest = {
      address: newAddress,
      pw: parseInt(password),
      orderUuid: orderNumber,
    };

    console.log(request);

    try {
      const response = await fetch(`http://localhost:8080/api/v1/order`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
      });

      const responseBody = await response.json();

      if (responseBody.statusCode === 200) {
        alert("배송주소가 성공적으로 업데이트되었습니다.");
        setShowAddressForm(false);
        setNewAddress("");
        // 주문 정보 새로고침
        handleSubmit(new Event("submit") as any);
      } else {
        alert(responseBody.message || "배송주소 업데이트에 실패했습니다.");
      }
    } catch (error) {
      console.error("배송주소 업데이트 중 오류:", error);
      alert("배송주소 업데이트 중 오류가 발생했습니다.");
    } finally {
      setIsUpdating(false);
    }
  };

  const handleOrderCancel = async () => {
    if (!order) return;
    if (!confirm("주문을 취소하시겠습니까?")) return;

    const request: OrderCancelReqeust = {
      pw: parseInt(password),
      orderUuid: orderNumber,
    };

    try {
      const response = await fetch(`http://localhost:8080/api/v1/order`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
      });

      const responseBody = await response.json();

      if (responseBody.statusCode === 200) {
        alert("주문이 성공적으로 취소되었습니다.");
        // 주문 정보 새로고침
        handleSubmit(new Event("submit") as any);
      } else {
        alert(responseBody.message || "주문 취소에 실패했습니다.");
      }
    } catch (error) {
      console.error("주문 취소 중 오류:", error);
      alert("주문 취소 중 오류가 발생했습니다.");
    }
  };

  const getStatusText = (status: string) => {
    const statusMap: { [key: string]: string } = {
      WAITING: "배송 대기",
      PROCESSING: "처리중",
      COMPLETED: "완료",
      CANCELLED: "취소됨",
    };
    return statusMap[status] || status;
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
            type="text"
            value={orderNumber}
            onChange={(e) => setOrderNumber(e.target.value)}
            placeholder="주문번호를 입력해주세요"
            className="flex-1 px-4 py-2 border border-gray-300 rounded-lg text-gray-900 focus:outline-none focus:ring-2 focus:ring-gray-600"
            required
          />
          <input
            type="password"
            value={password}
            onChange={(e) => {
              const value = e.target.value;
              if (/^\d*$/.test(value) && value.length <= 4) {
                setPassword(value);
              }
            }}
            placeholder="주문 비밀번호를 입력해주세요"
            className="flex-1 px-4 py-2 border border-gray-300 rounded-lg text-gray-900 focus:outline-none focus:ring-2 focus:ring-gray-600"
            required
          />
          <button
            type="submit"
            className="px-6 py-2 bg-gray-900 text-white rounded-lg hover:bg-gray-600 transition-colors"
          >
            주문 조회
          </button>
        </div>
      </form>

      {order && (
        <div>
          {/* 주문 상태 및 버튼 영역 */}
          <div className="mb-4 flex items-center justify-between">
            <div className="flex items-center gap-4">
              <span className="text-sm font-medium px-3 py-1 rounded-full bg-gray-100 text-black">
                {getStatusText(order.orderStatus)}
              </span>
            </div>
            {order.orderStatus === "WAITING" && (
              <div className="space-x-3">
                <button
                  onClick={() => setShowAddressForm(true)}
                  className="text-blue-600 hover:text-blue-700 text-sm font-medium"
                >
                  수정
                </button>
                <button
                  onClick={handleOrderCancel}
                  className="text-red-600 hover:text-red-700 text-sm font-medium"
                >
                  취소
                </button>
              </div>
            )}
          </div>

          {/* 배송주소 수정 폼 */}
          {showEmailForm && (
            <div className="mb-4 border border-gray-100 rounded-lg shadow-sm">
              <div className="px-4 py-3 border-b border-gray-100">
                <h3 className="text-sm font-medium text-gray-900">
                  배송주소 수정
                </h3>
              </div>
              <form onSubmit={handleAddressSubmit} className="p-4">
                <div className="space-y-4">
                  <div>
                    <label className="block text-sm text-gray-600 mb-1">
                      배송주소
                    </label>
                    <input
                      type="text"
                      value={newAddress}
                      onChange={(e) => setNewAddress(e.target.value)}
                      className="w-full px-4 py-2 border border-gray-300 rounded-lg text-gray-900 focus:outline-none focus:ring-2 focus:ring-gray-600"
                      placeholder="새 배송주소를 입력해주세요"
                      required
                    />
                  </div>
                  <div className="flex justify-end gap-3">
                    <button
                      type="button"
                      onClick={() => setShowAddressForm(false)}
                      className="px-4 py-2 text-sm font-medium text-gray-900 bg-white border border-gray-300 rounded-lg hover:bg-gray-50"
                    >
                      취소
                    </button>
                    <button
                      type="submit"
                      disabled={isUpdating}
                      className="px-4 py-2 text-sm font-medium text-white bg-gray-900 rounded-lg hover:bg-gray-600 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
                    >
                      {isUpdating ? "처리중..." : "저장"}
                    </button>
                  </div>
                </div>
              </form>
            </div>
          )}

          {/* 주문 상세 내역 */}
          <div className="space-y-6">
            <OrderItem order={order} />
          </div>
        </div>
      )}

      {!order && (
        <div className="text-center py-12 text-gray-500">
          주문 내역이 없거나 이메일을 입력해주세요.
        </div>
      )}
    </div>
  );
}
