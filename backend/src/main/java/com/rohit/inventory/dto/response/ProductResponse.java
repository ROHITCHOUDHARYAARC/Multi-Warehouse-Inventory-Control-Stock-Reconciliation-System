package com.rohit.inventory.dto.response;
import com.rohit.inventory.entity.ProductStatus; import java.util.UUID;
public record ProductResponse(UUID id, String sku, String name, String barcode, UUID categoryId, String categoryName, ProductStatus status) { }
