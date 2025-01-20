package com.ll.cafeservice.domain.admin.controller.v1;

import com.ll.cafeservice.api.Result;
import com.ll.cafeservice.domain.admin.dto.request.LoginRequest;
import com.ll.cafeservice.domain.admin.dto.response.LoginResponse;
import com.ll.cafeservice.domain.admin.service.AdminService;
import com.ll.cafeservice.entity.admin.Admin;
import com.ll.cafeservice.global.security.jwt.JwtProvider;
import com.ll.cafeservice.global.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        CookieUtil.addCookie(response, "access_key", token, 60 * 60);

        LoginResponse loginResponse = new LoginResponse(admin.getUsername(), token);
        return Result.success(loginResponse);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "관리자 로그아웃 API")
    public Result<String> logout(HttpServletResponse response) throws IOException {
        CookieUtil.removeCookie(response, "access_key");
        response.sendRedirect("/");
        return Result.success("Operation successful.");
    }
}
