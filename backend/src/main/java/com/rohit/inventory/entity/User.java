package com.rohit.inventory.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "users")
public class User extends BaseEntity {
    @Column(nullable = false, unique = true, length = 80) private String username;
    @Column(nullable = false, unique = true, length = 255) private String email;
    @Column(name = "password_hash", nullable = false, length = 255) private String passwordHash;
    @Column(name = "first_name", nullable = false, length = 100) private String firstName;
    @Column(name = "last_name", nullable = false, length = 100) private String lastName;
    @Column(length = 30) private String phone;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private UserStatus status = UserStatus.ACTIVE;
    @Column(name = "last_login_at") private Instant lastLoginAt;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_warehouses", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "warehouse_id"))
    private Set<Warehouse> assignedWarehouses = new HashSet<>();
    public String getUsername() { return username; } public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; } public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; } public String getPhone() { return phone; } public UserStatus getStatus() { return status; }
    public Set<Role> getRoles() { return roles; }
    public Set<Warehouse> getAssignedWarehouses() { return assignedWarehouses; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setStatus(UserStatus status) { this.status = status; }
    public void setLastLoginAt(Instant lastLoginAt) { this.lastLoginAt = lastLoginAt; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
    public void setAssignedWarehouses(Set<Warehouse> assignedWarehouses) { this.assignedWarehouses = assignedWarehouses; }
}
