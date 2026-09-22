package com.rohit.inventory.repository;
import com.rohit.inventory.entity.Category; import java.util.UUID; import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryRepository extends JpaRepository<Category, UUID> { }
