package com.example.student_management.exception;

import com.example.student_management.dto.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice // áp dụng global cho tất cả controller
public class GlobalExceptionHandler {

    // lỗi validation (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(400, "Dữ liệu không hợp lệ", errors));
    }

    // lỗi bạn chủ động throw (ResponseStatusException)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatus(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(new ApiResponse<>(
                        ex.getStatusCode().value(),
                        ex.getReason(),
                        null
                ));
    }

    // lỗi database (trùng dữ liệu, khóa ngoại...)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDatabaseError(DataIntegrityViolationException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(400, "Dữ liệu bị trùng hoặc vi phạm ràng buộc", null));
    }

    // lỗi JSON sai format (date sai, kiểu dữ liệu sai)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidJson(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(400, "Dữ liệu gửi lên không hợp lệ", null));
    }

    // lỗi RuntimeException (bổ sung từ file cũ)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(500, "Lỗi hệ thống: " + ex.getMessage(), null));
    }

    // lỗi chung (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAll(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(500, "Lỗi hệ thống: " + ex.getMessage(), null));
    }
}
