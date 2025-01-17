package com.ll.cafeservice.domain.admin.controller.v1;

import com.ll.cafeservice.api.Result;
import com.ll.cafeservice.domain.admin.dto.request.LoginRequest;
import com.ll.cafeservice.domain.admin.dto.response.LoginResponse;
import com.ll.cafeservice.domain.admin.service.AdminService;
import com.ll.cafeservice.entity.admin.Admin;
import com.ll.cafeservice.global.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AdminControllerV1", description = "API 관리자 관련 컨트롤러")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminControllerV1 {

    private final AdminService adminService;
    private final JwtProvider jwtProvider;


    @PostMapping("/login")
    @Operation(summary = "로그인", description = "관리자 로그인 API")
    public Result<LoginResponse> login(@RequestBody @Valid LoginRequest request,
        HttpServletResponse response) {
        Admin admin = adminService.authenticate(request.username(), request.password());
        String token = jwtProvider.generateToken(admin.getUsername());

        // TODO : 현재 구현 진행하면서 직접 토큰값을 넘기면서 진행중인데 병합 이후에 쿠키를 통해 데이터를 교환할 예정
//        Cookie cookie = new Cookie("access_key", token);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setPath("/");
//        cookie.setMaxAge(60 * 60);
//        response.addCookie(cookie);

        LoginResponse loginResponse = new LoginResponse(admin.getUsername(), token);
        return Result.success(loginResponse);
    }

    @GetMapping("/test")
    public String main() {
        return "test";
    }
}
