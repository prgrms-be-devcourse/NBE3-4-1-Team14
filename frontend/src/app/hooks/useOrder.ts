import { useRouter } from 'next/navigation';
import { useCart } from './useCart';
import { Product, Order, OrderItem } from '../types';

export function useOrder() {
  const router = useRouter();
  const { cartCounts, setCartCounts } = useCart();

  const createOrder = (products: Product[]) => {
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

    const order: Order = {
      orderId: Date.now(),
      orderDate: new Date().toISOString(),
      items: orderItems,
      totalAmount: orderItems.reduce((sum, item) => sum + item.totalPrice, 0)
    };

    const existingOrders = JSON.parse(localStorage.getItem('orders') || '[]');
    localStorage.setItem('orders', JSON.stringify([...existingOrders, order]));

    return true;
  };

  const handleCheckout = (products: Product[]) => {
    if (createOrder(products)) {
      setCartCounts({});
      alert('주문이 완료되었습니다!');
      router.push('/orders');
    }
  };

  return { handleCheckout };
}