package com.rohit.inventory.dto.response;
import java.time.Instant; import java.util.Map;
public record ErrorResponse(boolean success, String message, String errorCode, String path, Map<String, String> fieldErrors, Instant timestamp) { }
