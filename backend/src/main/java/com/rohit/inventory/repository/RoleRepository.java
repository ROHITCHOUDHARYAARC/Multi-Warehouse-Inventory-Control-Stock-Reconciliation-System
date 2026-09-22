package com.rohit.inventory.repository;
import com.rohit.inventory.entity.Role; import java.util.Optional; import java.util.UUID; import org.springframework.data.jpa.repository.JpaRepository;
public interface RoleRepository extends JpaRepository<Role, UUID> { Optional<Role> findByCode(String code); }
