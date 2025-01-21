import React, { useState } from "react";
import ProductModal from "./ProductModal";
import { handleCreateProduct, handleUpdateProduct, handleDeleteProduct } from "./productHandlers";
import { ProductDetail } from "../types/board";

interface ProductHandlersProps {
    selectedProductId: number | null;
    setSelectedProductId: React.Dispatch<React.SetStateAction<number | null>>;
    products: ProductDetail[];
    setProducts: React.Dispatch<React.SetStateAction<ProductDetail[]>>;
    setError: React.Dispatch<React.SetStateAction<string>>;
}

const ProductList: React.FC<ProductHandlersProps> = ({
                                                             selectedProductId,
                                                             setSelectedProductId,
                                                             products,
                                                             setProducts,
                                                             setError,
                                                         }) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [currentProduct, setCurrentProduct] = useState<ProductDetail | null>(null);

    const openModal = (product: ProductDetail | null) => {
        setCurrentProduct(product);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setCurrentProduct(null);
        setIsModalOpen(false);
    };

    const handleRegisterProduct = () => {
        openModal({
            id: null,
            name: "",
            description: "",
            price: 0,
            quantity: 0,
            filename: "",
        });
    };

    const handleEditProduct = () => {
        if (selectedProductId !== null) {
            const product = products.find((p) => p.id === selectedProductId);
            if (product) {
                openModal(product);
            } else {
                alert("선택한 상품을 찾을 수 없습니다.");
            }
        } else {
            alert("수정할 상품을 선택하세요.");
        }
    };

    const handleDeleteProductHandler = () => {
        if (selectedProductId !== null) {
            handleDeleteProduct(selectedProductId, setProducts, setError);
        } else {
            alert("삭제할 상품을 선택하세요.");
        }
    };

    return (
        <>
            <div className="flex justify-items-start space-x-3 mt-3 text-s">
                <button
                    onClick={handleRegisterProduct}
                    className="bg-indigo-400 text-white py-1 px-4 rounded-md hover:bg-indigo-700 focus:outline-none"
                >
                    상품 등록
                </button>
                <button
                    onClick={handleEditProduct}
                    className="bg-blue-400 text-white py-1 px-4 rounded-md hover:bg-blue-700 focus:outline-none"
                >
                    상품 정보 수정
                </button>
                <button
                    onClick={handleDeleteProductHandler}
                    className="bg-red-400 text-white py-1 px-4 rounded-md hover:bg-red-700 focus:outline-none"
                >
                    상품 삭제
                </button>
            </div>

            {isModalOpen && currentProduct && (
                <ProductModal
                    product={currentProduct}
                    onSave={async (product) => {
                        try {
                            if (product.id === null) {
                                await handleCreateProduct(product, setProducts, setError);
                            } else {
                                await handleUpdateProduct(product, setProducts, setError);
                            }
                            closeModal();
                        } catch (error) {
                            console.error("Error during save operation:", error);
                            setError("상품 저장 중 오류가 발생했습니다.");
                        }
                    }}
                    onClose={closeModal}
                />
            )}
        </>
    );
};

export default ProductList;
