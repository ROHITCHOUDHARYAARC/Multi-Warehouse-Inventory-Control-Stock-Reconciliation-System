package com.rohit.inventory.exception;

import com.rohit.inventory.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant; import java.util.LinkedHashMap; import java.util.Map;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler; import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ResourceNotFoundException.class) ResponseEntity<ErrorResponse> notFound(ResourceNotFoundException ex, HttpServletRequest r) { return error(HttpStatus.NOT_FOUND, ex.getMessage(), "RESOURCE_NOT_FOUND", Map.of(), r); }
    @ExceptionHandler(DuplicateResourceException.class) ResponseEntity<ErrorResponse> duplicate(DuplicateResourceException ex, HttpServletRequest r) { return error(HttpStatus.CONFLICT, ex.getMessage(), "DUPLICATE_RESOURCE", Map.of(), r); }
    @ExceptionHandler(InsufficientStockException.class) ResponseEntity<ErrorResponse> insufficient(InsufficientStockException ex, HttpServletRequest r) { return error(HttpStatus.CONFLICT, ex.getMessage(), "INSUFFICIENT_STOCK", Map.of(), r); }
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ErrorResponse> invalid(MethodArgumentNotValidException ex, HttpServletRequest r) { Map<String,String> errors = new LinkedHashMap<>(); ex.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage())); return error(HttpStatus.BAD_REQUEST, "Request validation failed", "VALIDATION_ERROR", errors, r); }
    @ExceptionHandler({IllegalArgumentException.class, DataIntegrityViolationException.class}) ResponseEntity<ErrorResponse> badRequest(Exception ex, HttpServletRequest r) { return error(HttpStatus.BAD_REQUEST, "The request could not be processed", "INVALID_REQUEST", Map.of(), r); }
    @ExceptionHandler(AccessDeniedException.class) ResponseEntity<ErrorResponse> forbidden(AccessDeniedException ex, HttpServletRequest r) { return error(HttpStatus.FORBIDDEN, "You do not have permission to perform this action", "ACCESS_DENIED", Map.of(), r); }
    @ExceptionHandler(AuthenticationException.class) ResponseEntity<ErrorResponse> unauthenticated(AuthenticationException ex, HttpServletRequest r) { return error(HttpStatus.UNAUTHORIZED, "Invalid credentials", "INVALID_CREDENTIALS", Map.of(), r); }
    @ExceptionHandler(Exception.class) ResponseEntity<ErrorResponse> unexpected(Exception ex, HttpServletRequest r) { log.error("Unexpected application error", ex); return error(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", "INTERNAL_ERROR", Map.of(), r); }
    private ResponseEntity<ErrorResponse> error(HttpStatus status, String message, String code, Map<String,String> fields, HttpServletRequest r) { return ResponseEntity.status(status).body(new ErrorResponse(false, message, code, r.getRequestURI(), fields, Instant.now())); }
}
