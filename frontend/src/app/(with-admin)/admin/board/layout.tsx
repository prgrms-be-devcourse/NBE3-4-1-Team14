import NavigationBar from "./components/NavigationBar";

export default function BoardLayout({ children }: { children: React.ReactNode }) {
    return (
        <div className="flex flex-col min-h-screen">
            {/* 네비게이션 바 */}
            <NavigationBar />
            {/* 동적 페이지 컨텐츠 */}
            <main className="flex-1 pt-16 px-4 sm:px-6 lg:px-8 max-w-7xl mx-auto">
                {children}
            </main>
        </div>
    );
}
