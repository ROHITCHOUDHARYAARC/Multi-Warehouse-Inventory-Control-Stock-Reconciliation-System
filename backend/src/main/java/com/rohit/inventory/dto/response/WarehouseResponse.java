package com.rohit.inventory.dto.response;
import com.rohit.inventory.entity.WarehouseStatus; import java.math.BigDecimal; import java.util.UUID;
public record WarehouseResponse(UUID id, String code, String name, String city, WarehouseStatus status, BigDecimal capacityQuantity) { }
