import { createProduct, updateProduct, deleteProduct } from "./productAPI";
import { ProductDetail } from "../types/board";

// 상품 등록 처리 함수
export const handleCreateProduct = async (
    product: ProductDetail,
    setProducts: React.Dispatch<React.SetStateAction<ProductDetail[]>>,
    setError: React.Dispatch<React.SetStateAction<string>>
) => {
    try {
        // FormData 생성 및 데이터 추가
        const formData = new FormData();
        formData.append("name", product.name || "");
        formData.append("description", product.description || "");
        formData.append("price", product.price?.toString() || "0");
        formData.append("quantity", product.quantity?.toString() || "0");

        // 이미지 추가
        if (product.image instanceof File) {
            formData.append("image", product.image); // 이미지 파일 추가
        } else {
            console.warn("이미지가 File 형식이 아닙니다. 업로드 생략");
        }

        console.log("FormData 객체:");
        for (let [key, value] of formData.entries()) {
            console.log(key, value);
        }

        // FormData 전달
        const newProduct = await createProduct(formData);

        console.log("서버 반환 객체:", newProduct);
        setProducts((prev) => [...prev, newProduct]);
    } catch (error) {
        console.error("Error during product creation:", error);
        if (error instanceof Error) {
            setError(error.message || "상품 등록 중 오류가 발생했습니다.");
        } else {
            setError("상품 등록 중 알 수 없는 오류가 발생했습니다.");
        }
    }
};

// 상품 수정 처리 함수
export const handleUpdateProduct = async (
    product: ProductDetail,
    setProducts: React.Dispatch<React.SetStateAction<ProductDetail[]>>,
    setError: React.Dispatch<React.SetStateAction<string>>
) => {
    if (!product.id) {
        setError("상품 ID가 유효하지 않습니다.");
        return;
    }

    try {
        // FormData 생성 및 데이터 추가
        const formData = new FormData();
        formData.append("name", product.name || "");
        formData.append("description", product.description || "");
        formData.append("price", product.price?.toString() || "0");
        formData.append("quantity", product.quantity?.toString() || "0");

        // 이미지 추가
        if (product.image instanceof File) {
            formData.append("image", product.image);
        } else {
            console.warn("이미지가 File 형식이 아닙니다. 업로드 생략");
        }

        await updateProduct(product.id, formData);

        // 상태 업데이트
        setProducts((prev) =>
            prev.map((p) => (p.id === product.id ? { ...p, ...product } : p))
        );
    } catch (error) {
        console.error("Error during product update:", error);
        if (error instanceof Error) {
            setError(error.message || "상품 수정 중 오류가 발생했습니다.");
        } else {
            setError("상품 수정 중 알 수 없는 오류가 발생했습니다.");
        }
    }
};

// 상품 삭제 처리 함수
export const handleDeleteProduct = async (
    selectedProductId: number,
    setProducts: React.Dispatch<React.SetStateAction<ProductDetail[]>>,
    setError: React.Dispatch<React.SetStateAction<string>>
) => {
    if (!selectedProductId) {
        alert("삭제할 상품을 선택하세요.");
        return;
    }

    if (confirm("정말 삭제하시겠습니까?")) {
        try {
            await deleteProduct(selectedProductId);

            // 상태 업데이트
            setProducts((prev) => prev.filter((product) => product.id !== selectedProductId));
        } catch (error) {
            console.error("Error during product deletion:", error);
            if (error instanceof Error) {
                setError(error.message || "상품 삭제 중 오류가 발생했습니다.");
            } else {
                setError("상품 삭제 중 알 수 없는 오류가 발생했습니다.");
            }
        }
    }
};
