package com.rohit.inventory.dto.request;
import com.rohit.inventory.entity.UserStatus; import jakarta.validation.constraints.NotNull;
public record UpdateUserStatusRequest(@NotNull UserStatus status) { }
