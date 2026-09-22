package com.rohit.inventory.dto.response;
import java.math.BigDecimal; import java.util.UUID;
public record ReplenishmentRecommendationResponse(UUID productId,String sku,String productName,UUID sourceWarehouseId,String sourceWarehouse,UUID destinationWarehouseId,String destinationWarehouse,BigDecimal suggestedQuantity,BigDecimal destinationAvailable,BigDecimal reorderLevel,int priorityScore,String reason){}
