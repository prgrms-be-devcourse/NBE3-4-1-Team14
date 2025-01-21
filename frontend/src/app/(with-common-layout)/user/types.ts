import type { ReactNode } from "react";

export interface Product {
  id: number;
  name: string;
  price: number;
  description: string;
  filename: string;
  imageUrl: string;
}

// 상품 상세데이터 타입 정의
export interface ProductDetail {
  id: number | null;            // 상품 ID
  name: string;                 // 제품 이름
  description: string;          // 제품 설명
  price: number;                // 가격
  quantity: number;             // 수량
  imgFilename?: string;         // 서버에 저장된 이미지 파일 이름
  imgFile?: File;               // 업로드할 이미지 파일
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
  quantity: number;
}

export interface OrderItemInfo{
  id: number;
  name : string;
  price : number;
  quantity: number;
}

export interface Order {
  id: number;
  email: string;
  items: OrderItemInfo[];
  uuid: string;
  orderStatus: string;
  address: string;
  totalPrice: number;
  orderDate: string;
}

export interface ClientPageProps {
  products: Product[];
}

// 타입 정의
export interface CartState {
  [key: number]: number;
}

export interface CartContextType {
  cartCounts: CartState;
  setCartCounts: React.Dispatch<React.SetStateAction<CartState>>;
  selectedCounts: CartState;
  setSelectedCounts: React.Dispatch<React.SetStateAction<CartState>>;
}

export interface ClientLayoutProps {
  children: ReactNode;
}