import { useRouter } from 'next/navigation';
import { useCart } from './useCart';
import { Product, OrderItem } from '../types';

interface OrderRequest{
  orderDate: string;
  items: OrderItem[];
  totalAmount: number;
  email: string;
  address : string;
}

export function useOrder() {
  const router = useRouter();
  const { cartCounts, setCartCounts } = useCart();

  const createOrder = async (products: Product[], email: string, address: string) => {
    console.log('createOrder called with:', { products, email, address });

    if (Object.keys(cartCounts).length === 0) {
      alert('상품을 담아주세요!');
      return false;
    }

    const orderItems: OrderItem[] = Object.entries(cartCounts).map(([id, count]) => {
      const product = products.find(p => p.id === Number(id));
      return {
        productId: Number(id),
        productName: product?.name || '',
        quantity: count,
        price: product?.price || 0,
        totalPrice: (product?.price || 0) * count
      };
    });

    const order: OrderRequest = {
      orderDate: new Date().toISOString(),
      items: orderItems,
      totalAmount: orderItems.reduce((sum, item) => sum + item.totalPrice, 0),
      email : email,
      address : address
    };

    console.log('Sending order:', order); // 요청 데이터 확인

    try {
      const response = await fetch('http://localhost:8080/api/v1/order', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(order),
      });
      if (!response.ok) {
        throw new Error('주문 생성에 실패했습니다.');
      }

      const result = await response.json();
      if (result.statusCode !== 200) {
        throw new Error(result.message);
      }

      // TODO : response로 넘어오는 주문 번호를 alert 한다.

      return true;
    } catch (error) {
      console.error('주문 생성 중 오류 발생:', error);
      alert('주문 생성에 실패했습니다.');
      return false;
    }
  };

  const handleCheckout = async (products: Product[], email : string, address : string) => {

    // 디버깅을 위한 로그 추가
    console.log('handleCheckout called with:', { products, email, address });

    // validation 추가
    if (!email || !address) {
      alert('이메일과 주소를 입력해주세요.');
      return;
    }

    const success = await createOrder(products, email, address);
    if (success) {
      setCartCounts({});
      alert('주문이 완료되었습니다!');
      router.push('/orders');
    }
  };

  return { handleCheckout };
}