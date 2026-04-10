package com.example.student_management.config;

import com.example.student_management.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ApiResponseWrapper implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    public ApiResponseWrapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType contentType,
                                  Class<? extends HttpMessageConverter<?>> converterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        // Nếu đã đúng format rồi → bỏ qua
        if (body instanceof ApiResponse) {
            return body;
        }

        // Fix lỗi khi return String
        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(
                        new ApiResponse<>(200, "Success", body)
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // Nếu body có phương thức getMessage (ví dụ StudentResponse, DeleteResponse...)
        String message = "Success";
        try {
            var method = body.getClass().getMethod("getMessage");
            Object msgObj = method.invoke(body);
            if (msgObj != null) {
                message = msgObj.toString();
            }
        } catch (Exception ignored) {
            // không có getMessage → giữ "Success"
        }

        return new ApiResponse<>(200, message, body);
    }
}
