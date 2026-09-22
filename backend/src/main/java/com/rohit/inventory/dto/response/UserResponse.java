package com.rohit.inventory.dto.response;
import com.rohit.inventory.entity.UserStatus; import java.time.Instant; import java.util.List; import java.util.UUID;
public record UserResponse(UUID id, String username, String email, String firstName, String lastName, String phone, UserStatus status, List<String> roles, List<WarehouseSummary> warehouses, Instant createdAt, Instant updatedAt) { public record WarehouseSummary(UUID id, String code, String name) { } }
