"use client";
// store/useProductStore.ts
import { create } from 'zustand'
import { Product } from "../types"

interface ProductState {
  products: Product[]
  setProducts: (products: Product[]) => void
  getProduct: (id: number) => Product | undefined
}

export const useProductStore = create<ProductState>((set, get) => ({
  products: [],
  setProducts: (products) => set({ products }),
  getProduct: (id) => get().products.find(product => product.id === id)
}))