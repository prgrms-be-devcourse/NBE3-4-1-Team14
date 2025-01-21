import type { Metadata } from "next";
import ClientLayout from "../ClientLayout";

export const metadata: Metadata = {
    title: "Grids & Circles",
    description: "당신을 위한 최고의 쇼핑 경험",
};

export default function RootLayout({
                                       children,
                                   }: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <ClientLayout>{children}</ClientLayout>
    );
}
