import { Order } from "../../types";
import ClientPage from "./ClientPage";

interface OrderResponse {
  statusCode: number;
  message: string;
  data: Order[];
}

export default async function Page() {
  const handleSubmit = async (e: string) => {
    const response = await fetch("http://localhost:7070/api/v1/order/" + e);
    if (!response.ok) {
      console.log(response.status);
      return;
    }

    const responseBody: OrderResponse = await response.json();
    if (responseBody.statusCode != 200) {
      alert("서버에 요청이 잘못되었습니다.");
      return;
    }
  };

  return <ClientPage />;
}
