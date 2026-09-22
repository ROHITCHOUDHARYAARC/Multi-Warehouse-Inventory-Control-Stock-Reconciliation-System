package com.rohit.inventory.mapper;
import com.rohit.inventory.dto.response.RoleResponse; import com.rohit.inventory.entity.Role; import org.springframework.stereotype.Component;
@Component public class RoleMapper { public RoleResponse toResponse(Role r) { return new RoleResponse(r.getId(),r.getCode(),r.getName(),r.getDescription(),r.isSystem(),r.getPermissions().stream().map(p->p.getCode()).sorted().toList()); } }
