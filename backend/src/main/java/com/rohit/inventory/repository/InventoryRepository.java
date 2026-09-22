package com.rohit.inventory.repository;
import com.rohit.inventory.entity.Inventory;
import jakarta.persistence.LockModeType;
import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface InventoryRepository extends JpaRepository<Inventory,UUID>{
 Optional<Inventory> findByWarehouseIdAndZoneIdAndProductId(UUID warehouseId,UUID zoneId,UUID productId);
 Page<Inventory> findByWarehouseId(UUID warehouseId,Pageable pageable);
 @EntityGraph(attributePaths={"warehouse","product"}) @Query("select i from Inventory i where i.product.status = com.rohit.inventory.entity.ProductStatus.ACTIVE") List<Inventory> findAllActiveWithLocation();
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select i from Inventory i where i.warehouse.id=:warehouseId and i.zone.id=:zoneId and i.product.id=:productId") Optional<Inventory> lockByLocation(@Param("warehouseId") UUID warehouseId,@Param("zoneId") UUID zoneId,@Param("productId") UUID productId);
}
