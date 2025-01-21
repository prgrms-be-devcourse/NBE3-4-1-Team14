import ClientPage from "./ClientPage";
import { Product } from "../types";
import { useProductStore } from "../store/useProductStore";

interface ProductResponse {
  statusCode: number;
  message: string;
  data: Product[];
}

export default async function Page() {
  try {
    // Step 1. Product 리스트 요청
    const response = await fetch("http://localhost:8080/api/v1/product/list");
    if (!response.ok) {
      console.error("Product 리스트 요청 실패:", response.status);
      return;
    }

    const responseBody: ProductResponse = await response.json();
    if (responseBody.statusCode !== 200) {
      console.error("서버 응답 에러:", responseBody.statusCode);
      return;
    }

    // Step 2. 각 Product에 대한 이미지 요청
    const productsWithImages = await Promise.all(
      responseBody.data.map(async (product) => {
        try {
          const imageResponse = await fetch(
            `http://localhost:8080/api/v1/image/${product.filename}`
          );

          if (!imageResponse.ok) {
            return product;
          }

          const imageData = await imageResponse.json();
          const base64String = imageData.data.contentAsByteArray;

          // 파일 확장자명을 확인해서 정해준다.
          const fileExtension = product.filename
            .split(".")
            .pop()
            ?.toLowerCase();
          let contentType = "image/png"; // 기본값
          switch (fileExtension) {
            case "jpg":
            case "jpeg":
              contentType = "image/jpeg";
              break;
          }

          const base64Url = `data:${contentType};base64,${base64String}`;

          return {
            ...product,
            imageUrl: base64Url,
          };
        } catch (error) {
          console.error(`이미지 처리 중 에러 (${product.filename}):`, error);
          return product;
        }
      })
    );
    return <ClientPage products={productsWithImages} />;
  } catch (error) {
    console.error("데이터 로드 중 에러:", error);
    return <div>에러가 발생했습니다.</div>;
  }
}
