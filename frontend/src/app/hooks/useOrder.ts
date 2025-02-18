import { useRouter } from 'next/navigation';
import { useCart } from './useCart';
import { Product, OrderItem } from '../types';

interface OrderRequest{
  orderDate: string;
  orderItems: OrderItem[];
  pw: string;
  email: string;
  address : string;
}

export function useOrder() {
  const router = useRouter();
  const { cartCounts, setCartCounts } = useCart();

  const createOrder = async (products: Product[], email: string, address: string, password: string) => {
    console.log('createOrder called with:', { products, email, address, password });

    if (Object.keys(cartCounts).length === 0) {
      alert('상품을 담아주세요!');
      return false;
    }

    const orderItems: OrderItem[] = Object.entries(cartCounts).map(([id, count]) => {
      const product = products.find(p => p.id === Number(id));
      return {
        productId: Number(id),
        quantity: count,
      };
    });

    const order: OrderRequest = {
      orderDate: new Date().toISOString(),
      orderItems: orderItems,
      pw : password,
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

      // status로 구체적인 에러 처리
      if (response.status === 400) {
        throw new Error('재고가 부족합니다.');
      }
      if (response.status === 404) {
        throw new Error('API를 찾을 수 없습니다');
      }
      if (response.status === 500) {
        throw new Error('서버 오류입니다');
      }

      const result = await response.json();
      if (result.statusCode !== 200) {
        throw new Error(result.message);
      }

      // TODO : response로 넘어오는 주문 번호를 alert 한다.
      const responseData = result.data;
      alert("주문번호 발급: " + responseData.uuid);

      return true;
    } catch (error) {
      if(error instanceof Error){
        console.error('주문 생성 중 오류 발생:', error);
        alert('주문 생성에 실패했습니다. 원인 : ' + error.message);
      }
      return false;
    }
  };

  const handleCheckout = async (products: Product[], email : string, address : string, password: string) => {

    // 디버깅을 위한 로그 추가
    console.log('handleCheckout called with:', { products, email, address , password});

    // validation 추가
    if (!email || !address || !password) {
      alert('이메일과 주소를 입력해주세요.');
      return;
    }

    const success = await createOrder(products, email, address, password);
    if (success) {
      setCartCounts({});
      alert('주문이 완료되었습니다. \n해당 주문은 다음 날 14시에 배송 시작됩니다!');
      router.push('/orders');
    }
  };

  return { handleCheckout };
}