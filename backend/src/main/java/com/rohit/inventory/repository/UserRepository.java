package com.rohit.inventory.repository;
import com.rohit.inventory.entity.User;
import com.rohit.inventory.entity.UserStatus; import java.util.Optional; import java.util.UUID;
import org.springframework.data.domain.Page; import org.springframework.data.domain.Pageable; import org.springframework.data.jpa.repository.JpaRepository; import org.springframework.data.jpa.repository.Query; import org.springframework.data.repository.query.Param;
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email); Optional<User> findByUsername(String username); boolean existsByEmail(String email); boolean existsByEmailAndIdNot(String email, UUID id);
    @Query("select distinct u from User u left join u.roles r where (:search is null or lower(u.email) like lower(concat('%', :search, '%')) or lower(u.username) like lower(concat('%', :search, '%')) or lower(u.firstName) like lower(concat('%', :search, '%')) or lower(u.lastName) like lower(concat('%', :search, '%'))) and (:status is null or u.status = :status) and (:role is null or r.code = :role)")
    Page<User> search(@Param("search") String search, @Param("status") UserStatus status, @Param("role") String role, Pageable pageable);
}
