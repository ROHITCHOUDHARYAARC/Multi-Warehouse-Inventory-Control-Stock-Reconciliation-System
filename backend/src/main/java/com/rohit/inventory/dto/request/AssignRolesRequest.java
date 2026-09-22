package com.rohit.inventory.dto.request;
import jakarta.validation.constraints.NotEmpty; import jakarta.validation.constraints.NotBlank; import java.util.Set;
public record AssignRolesRequest(@NotEmpty Set<@NotBlank String> roleCodes) { }
