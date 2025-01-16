import type { ReactNode } from "react";

export interface Product {
  id: number;
  name: string;
  price: number;
  description : string;
}

// 컴포넌트 Props 타입들
export interface ProductListProps {
  products: Product[];
}

export interface CartProps {
  products: Product[];
}

export interface CartItemProps {
  product: Product;
  count: number;
}

export interface CheckoutButtonProps {
  products: Product[];
}

export interface ProductItemProps {
  product: Product;
  onAddToCart: (product: Product) => void;
}

// 주문 관련 타입들
export interface OrderItem {
  productId: number;
  productName: string;
  quantity: number;
  price: number;
  totalPrice: number;
}

export interface Order {
  orderId: number;
  orderDate: string;
  items: OrderItem[];
  totalAmount: number;
  address : string;
  state : string;
}

export interface ClientPageProps {
  products: Product[];
}

// 타입 정의
export interface CartState {
  [key: number]: number;
}

export  interface CartContextType {
  cartCounts: CartState;
  setCartCounts: React.Dispatch<React.SetStateAction<CartState>>;
  selectedCounts: CartState;
  setSelectedCounts: React.Dispatch<React.SetStateAction<CartState>>;
}

export interface ClientLayoutProps {
  children: ReactNode;
}