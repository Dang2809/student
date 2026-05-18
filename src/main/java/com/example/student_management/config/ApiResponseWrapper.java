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

    private final ObjectMapper objectMapper; // Dùng để chuyển đổi đối tượng thành JSON

    public ApiResponseWrapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper; // Inject ObjectMapper từ Spring context
    }

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true; // Áp dụng cho tất cả response trả về
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType contentType,
                                  Class<? extends HttpMessageConverter<?>> converterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        // Nếu body đã là ApiResponse thì giữ nguyên, không wrap lại
        if (body instanceof ApiResponse) {
            return body;
        }

        // Nếu body là String thì phải serialize thủ công để tránh lỗi
        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(
                        new ApiResponse<>(200, "Success", body) // Wrap String vào ApiResponse
                );
            } catch (Exception e) {
                throw new RuntimeException(e); // Nếu lỗi thì throw RuntimeException
            }
        }

        // Nếu body có phương thức getMessage thì lấy message từ đó
        String message = "Success"; // Mặc định message là "Success"
        try {
            var method = body.getClass().getMethod("getMessage"); // Tìm method getMessage
            Object msgObj = method.invoke(body); // Gọi method để lấy giá trị
            if (msgObj != null) {
                message = msgObj.toString(); // Nếu có thì gán vào message
            }
        } catch (Exception ignored) {
            // Nếu không có getMessage thì giữ nguyên "Success"
        }

        // Trả về ApiResponse với body đã được wrap
        return new ApiResponse<>(200, message, body);
    }
}
