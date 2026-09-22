package com.rohit.inventory.controller;
import com.rohit.inventory.dto.response.ApiResponse; import com.rohit.inventory.dto.response.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping; import org.springframework.web.bind.annotation.RequestMapping; import org.springframework.web.bind.annotation.RestController;
@RestController @RequestMapping("/api/v1/health")
public class HealthController {
    @GetMapping public ApiResponse<HealthResponse> health() { return ApiResponse.success("Backend is operational", new HealthResponse("UP", "inventory-command-center")); }
}
