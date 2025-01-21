const API_BASE_URL = "http://localhost:8080/api/v1/admin/product"; // 수정된 URL 경로

// API 요청 함수
async function apiRequest(url: string, options: RequestInit): Promise<any> {
    const response = await fetch(`${API_BASE_URL}${url}`, {
        ...options,
        credentials: 'include' // 쿠키를 포함시키는 부분
    });

    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || "API 요청 중 오류가 발생했습니다.");
    }

    return response.json();
}

// 상품 목록 요청
export async function getProductList() {
    try {
        const response = await fetch("http://localhost:8080/api/v1/product/list", {
            method: "GET",
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "상품 목록 불러오기 실패.");
        }

        const result = await response.json();
        return result.data; // API 응답의 data 필드를 반환
    } catch (error) {
        console.error("상품 목록 불러오기:", error);
        throw error;
    }
}

// 품목 추가 요청
export async function createProduct(product: FormData) {
    console.log("상품 데이터:", product);

    // FormData에서 값을 추출
    const name = product.get("name") as string;
    const description = product.get("description") as string;
    const price = product.get("price") as string;
    const quantity = product.get("quantity") as String;
    const image = product.get("image") as File;

    if (!name || !description || !price) {
        throw new Error("필수 필드(name, description, price)가 누락되었습니다.");
    }

    const formData = new FormData();
    formData.append("name", name);
    formData.append("description", description);

    // price는 숫자여야 하므로 확인 후 변환
    if (!isNaN(Number(price))) {
        formData.append("price", Number(price).toString());
    } else {
        throw new Error("price는 유효한 숫자여야 합니다.");
    }
    formData.append("quantity", Number(quantity).toString());

    // 이미지 추가 여부 확인
    if (image) {
        if (image instanceof File) {
            formData.append("image", image);
        } else {
            console.warn("이미지가 File 형식이 아닙니다. 업로드를 생략합니다.");
        }
    }

    // API 요청
    return apiRequest("", { method: "POST", body: formData });
}

// 품목 수정 요청
export async function updateProduct(id: number, product: FormData) {
    console.log("수정 상품 데이터:", product);

    const formData = new FormData();
    formData.append("name", product.get("name") as string);
    formData.append("description", product.get("description") as string);
    formData.append("price", product.get("price") as string);
    formData.append("quantity", product.get("quantity") as string);
    if (product.get("image") instanceof File) {
        formData.append("image", product.get("image") as File);
    } else {
        console.warn("이미지가 File 형식이 아닙니다. 이미지 추가가 생략됩니다.");
    }
    return apiRequest(`/${id}`, { method: "PUT", body: formData });
}

// 품목 삭제 요청
export async function deleteProduct(id: number) {
    return apiRequest(`/${id}`, { method: "DELETE" });
}
