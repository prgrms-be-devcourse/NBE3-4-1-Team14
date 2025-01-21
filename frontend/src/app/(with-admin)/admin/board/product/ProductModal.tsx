import React, { useState } from "react";
import { ProductDetail } from "../types/board";

interface ProductModalProps {
    product: ProductDetail;
    onSave: (product: ProductDetail) => void;
    onClose: () => void;
}

const ProductModal: React.FC<ProductModalProps> = ({ product, onSave, onClose }) => {
    const [localProduct, setLocalProduct] = useState<ProductDetail>(product);
    const [imagePreview, setImagePreview] = useState<string | undefined>(

    product.filename ? `http://localhost:8080/api/v1/image/path/${product.filename}` : undefined
    );

    const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) {
            setLocalProduct({ ...localProduct, image: file });
            setImagePreview(URL.createObjectURL(file));
        }
    };

    const handleInputChange = (
        e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
    ) => {
        const { name, value } = e.target;
        setLocalProduct({
            ...localProduct,
            [name]: name === "price" || name === "quantity" ? Number(value) : value,
        });
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSave(localProduct);
    };

    return (
        <div className="fixed inset-0 bg-black/40 backdrop-blur-sm flex items-center justify-center z-50">
            <div className="bg-white w-4/5 max-w-3xl rounded-3xl shadow-xl overflow-hidden">
                <div className="px-8 py-6 border-b border-gray-100">
                    <h2 className="text-xl font-semibold text-gray-900">
                        {localProduct.id ? "상품 수정" : "신규 상품 등록"}
                    </h2>
                </div>

                <form onSubmit={handleSubmit} className="p-8">
                    <div className="flex gap-8">
                        <div className="flex-1 space-y-6">
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-2">
                                    상품명
                                </label>
                                <input
                                    type="text"
                                    name="name"
                                    value={localProduct.name}
                                    onChange={handleInputChange}
                                    className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:ring-2 focus:ring-blue-100 focus:border-blue-400 transition-colors"
                                    required
                                    placeholder="상품명을 입력하세요"
                                />
                            </div>

                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-2">
                                    상품 설명
                                </label>
                                <textarea
                                    name="description"
                                    value={localProduct.description}
                                    onChange={handleInputChange}
                                    className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:ring-2 focus:ring-blue-100 focus:border-blue-400 transition-colors"
                                    rows={4}
                                    required
                                    placeholder="상품에 대한 설명을 입력하세요"
                                />
                            </div>

                            <div className="flex gap-4">
                                <div className="flex-1">
                                    <label className="block text-sm font-medium text-gray-700 mb-2">
                                        가격
                                    </label>
                                    <div className="relative">
                                        <span className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-500">₩</span>
                                        <input
                                            type="number"
                                            name="price"
                                            value={localProduct.price}
                                            onChange={handleInputChange}
                                            className="w-full pl-8 pr-4 py-3 rounded-xl border border-gray-200 focus:ring-2 focus:ring-blue-100 focus:border-blue-400 transition-colors"
                                            required
                                            placeholder="0"
                                        />
                                    </div>
                                </div>
                                <div className="flex-1">
                                    <label className="block text-sm font-medium text-gray-700 mb-2">
                                        재고수량
                                    </label>
                                    <input
                                        type="number"
                                        name="quantity"
                                        value={localProduct.quantity}
                                        onChange={handleInputChange}
                                        className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:ring-2 focus:ring-blue-100 focus:border-blue-400 transition-colors"
                                        required
                                        placeholder="0"
                                    />
                                </div>
                            </div>
                        </div>

                        <div className="w-56 space-y-4">
                            <div
                                className="aspect-square w-full bg-gray-50 rounded-2xl border border-gray-200 overflow-hidden">
                                {imagePreview ? (
                                    <img
                                        src={imagePreview}
                                        alt="미리보기"
                                        className="w-full h-full object-cover"
                                    />
                                ) : (
                                    <div className="w-full h-full flex items-center justify-center text-gray-400">
                                        이미지 없음
                                    </div>
                                )}

                            </div>
                            {/*<div className="flex flex-col items-center">*/}
                            {/*    <p className="text-xs text-gray-500">Filename: {product.filename}</p>*/}
                            {/*    <img*/}
                            {/*        src={`http://localhost:8080/api/v1/image/path/${product.filename}`}*/}
                            {/*        alt={product.filename}*/}
                            {/*        className="w-16 h-16 rounded-lg object-cover ring-1 ring-gray-200"*/}
                            {/*    />*/}
                            {/*</div>*/}
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-2">
                                    이미지 첨부
                                </label>
                                <input
                                    type="file"
                                    onChange={handleImageChange}
                                    className="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4
                                    file:rounded-full file:border-0 file:text-sm file:font-semibold
                                    file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100"
                                    accept="image/*"
                                />
                            </div>
                        </div>
                    </div>

                    <div className="mt-8 flex justify-end gap-3">
                        <button
                            type="button"
                            onClick={onClose}
                            className="px-6 py-3 rounded-xl bg-gray-100 text-gray-700 hover:bg-gray-200 transition-colors">
                            취소
                        </button>
                        <button
                            type="submit"
                            className="px-6 py-3 rounded-xl bg-blue-600 text-white hover:bg-blue-700 transition-colors">
                        {localProduct.id ? "수정하기" : "등록하기"}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default ProductModal;