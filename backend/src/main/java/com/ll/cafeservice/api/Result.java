package com.ll.cafeservice.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "결과 반환용 객체")
public class Result<T> {

    @Schema(description = "상태 코드")
    @NonNull
    private int statusCode;

    @Schema(description = "클라 전달용 메시지")
    @NonNull
    private String message;

    @Schema(description = "반환 데이터")
    private T data;

    public static <T> Result<T> success(final T data) {
        return createResult(200, "Success", data);
    }

    public static <T> Result<T> error(final int statusCode, final String message) {
        return createResult(statusCode, message);
    }

    public static <T> Result<T> createResult(final int statusCode, final String message,
        final T data) {
        return Result.<T>builder()
            .statusCode(statusCode)
            .message(message)
            .data(data)
            .build();
    }

    public static <T> Result<T> createResult(final int statusCode, final String message) {
        return Result.<T>builder()
            .statusCode(statusCode)
            .message(message)
            .build();
    }

    public String toJson() {
        return String.format("{\"status\": %d, \"message\": \"%s\"}", statusCode, message);
    }
}