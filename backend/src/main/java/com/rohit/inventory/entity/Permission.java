package com.rohit.inventory.entity;

import jakarta.persistence.*;
@Entity @Table(name = "permissions")
public class Permission extends BaseEntity {
    @Column(nullable = false, unique = true, length = 120) private String code;
    @Column(nullable = false, length = 150) private String name;
    @Column(nullable = false, length = 80) private String module;
    private String description;
    public String getCode() { return code; } public String getName() { return name; }
}
