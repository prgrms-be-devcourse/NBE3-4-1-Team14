import { redirect } from "next/navigation";

// 관리자 페이지 루트 경로
// /admin으로 접근 시 /admin/login으로 리다이렉트
export default function AdminPage() {
    redirect("/admin/login"); // 서버에서 리다이렉트 처리
    return null; // 리다이렉트가 실행되므로 UI는 렌더링되지 않음
}
