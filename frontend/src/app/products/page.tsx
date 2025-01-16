import ClientPage from "./ClientPage";
import { Product } from "../types"; // Product 타입 임포트

interface ProductResponse {
  statusCode: number;
  message: string;
  data: Product[];
}

export default async function Page() {
  const response = await fetch("http://localhost:7070/api/v1/product/list");
  if (!response.ok) {
    console.log(response.status);
    return;
  }

  const responseBody: ProductResponse = await response.json();
  if (responseBody.statusCode != 200) {
    alert("서버에 요청이 잘못되었습니다.");
    return;
  }

  const products = responseBody.data;

  return <ClientPage products={products} />;
}
