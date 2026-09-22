package com.rohit.inventory.dto.response;
import java.util.List; import java.util.UUID;
public record RoleResponse(UUID id, String code, String name, String description, boolean system, List<String> permissions) { }
