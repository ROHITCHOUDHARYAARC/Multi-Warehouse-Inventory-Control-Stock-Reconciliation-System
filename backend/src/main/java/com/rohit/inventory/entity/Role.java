package com.rohit.inventory.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "roles")
public class Role extends BaseEntity {
    @Column(nullable = false, unique = true, length = 80) private String code;
    @Column(nullable = false, length = 120) private String name;
    private String description;
    @Column(name = "is_system", nullable = false) private boolean system = true;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions = new HashSet<>();
    public String getCode() { return code; } public String getName() { return name; } public String getDescription() { return description; } public boolean isSystem() { return system; }
    public Set<Permission> getPermissions() { return permissions; }
}
