const API_BASE_URL_ADMIN = "http://localhost:8080/api/v1/admin/product"; // 관리용 API 경로
const API_BASE_URL_PRODUCT = "http://localhost:8080/api/v1/product"; // 상품 조회용 경로

// API 요청 함수
async function apiRequest(baseURL: string, url: string, options: RequestInit): Promise<any> {
    const fullUrl = `${baseURL}${url.startsWith("/") ? url : `/${url}`}`;
    const response = await fetch(fullUrl, options);

    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || "API 요청 중 오류가 발생했습니다.");
    }

    return response.json();
}

// 상품 목록 요청
export async function getProductList() {
    try {
        const result = await apiRequest(API_BASE_URL_PRODUCT, "/list", { method: "GET" });
        return result.data; // API 응답의 data 필드를 반환
    } catch (error) {
        console.error("상품 목록 불러오기 실패:", error);
        throw error;
    }
}

// 품목 추가 요청
export async function createProduct(product: FormData) {
    console.log("상품 데이터:", product);

    const name = product.get("name") as string;
    const description = product.get("description") as string;
    const price = product.get("price") as string;
    const image = product.get("image") as File;

    if (!name || !description || !price) {
        throw new Error("필수 필드(name, description, price)가 누락되었습니다.");
    }

    const formData = new FormData();
    formData.append("name", name);
    formData.append("description", description);

    if (!isNaN(Number(price))) {
        formData.append("price", Number(price).toString());
    } else {
        throw new Error("price는 유효한 숫자여야 합니다.");
    }

    if (image instanceof File) {
        formData.append("image", image);
    }

    return apiRequest(API_BASE_URL_ADMIN, "", { method: "POST", body: formData });
}

// 품목 수정 요청
export async function updateProduct(id: number, product: FormData) {
    const formData = new FormData();
    formData.append("name", product.get("name") as string);
    formData.append("description", product.get("description") as string);
    formData.append("price", product.get("price") as string);
    if (product.get("image") instanceof File) {
        formData.append("image", product.get("image") as File);
    }

    return apiRequest(API_BASE_URL_ADMIN, `/${id}`, { method: "PUT", body: formData });
}

// 품목 삭제 요청
export async function deleteProduct(id: number) {
    return apiRequest(API_BASE_URL_ADMIN, `/${id}`, { method: "DELETE" });
}
