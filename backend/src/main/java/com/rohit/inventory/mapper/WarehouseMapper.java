package com.rohit.inventory.mapper;
import com.rohit.inventory.dto.response.WarehouseResponse; import com.rohit.inventory.entity.Warehouse; import org.springframework.stereotype.Component;
@Component public class WarehouseMapper { public WarehouseResponse toResponse(Warehouse warehouse) { return new WarehouseResponse(warehouse.getId(), warehouse.getCode(), warehouse.getName(), warehouse.getCity(), warehouse.getStatus(), warehouse.getCapacityQuantity()); } }
