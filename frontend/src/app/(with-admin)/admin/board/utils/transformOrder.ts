// utils/transformOrder.ts
import { OrderDetail, OrderItem } from "../types/board";

const transformOrderData = (apiOrder: any): OrderDetail => {
    return {
        id: apiOrder.id,
        orderDate: new Date(apiOrder.orderDateTime).toISOString(), // ISO 8601 포맷으로 변환
        uuid: apiOrder.orderUuid,
        email: apiOrder.email,
        address: apiOrder.address,
        totalPrice: apiOrder.totalPrice,
        items: apiOrder.orderItems.map((item: any): OrderItem => ({
            name: item.name,
            quantity: item.quantity,
            price: item.price,
        })), // 주문 품목을 배열로 변환
        orderStatus: apiOrder.status,
    };
};

export default transformOrderData;
