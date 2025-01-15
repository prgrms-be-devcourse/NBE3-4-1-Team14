package com.ll.cafeservice.domain.user.controller.v1;

import com.ll.cafeservice.api.Result;
import com.ll.cafeservice.domain.user.dto.request.LoginRequest;
import com.ll.cafeservice.domain.user.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UserControllerV1", description = "API 유저관련 컨트롤러")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserControllerV1 {

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "")
    public Result<LoginResponse> login(
            @RequestBody @Valid LoginRequest request
    ) {
        log.info("login request: {}", request.username());
        return Result.success(new LoginResponse(request.username()));
    }


}
