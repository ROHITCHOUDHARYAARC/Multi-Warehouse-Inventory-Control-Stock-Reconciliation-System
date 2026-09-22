package com.rohit.inventory.repository;
import com.rohit.inventory.entity.Alert; import java.util.*; import org.springframework.data.domain.*; import org.springframework.data.jpa.repository.*;
public interface AlertRepository extends JpaRepository<Alert,UUID>{Optional<Alert> findByAlertTypeAndWarehouseIdAndProductIdAndStatusIn(String type,UUID warehouseId,UUID productId,Collection<String> statuses); Page<Alert> findByStatus(String status,Pageable page); Page<Alert> findByStatusAndWarehouseId(String status,UUID warehouseId,Pageable page);}
