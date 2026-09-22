package com.rohit.inventory.dto.request;
import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.util.UUID;
/** Foundation request contract; its endpoint is intentionally introduced in the catalog module. */
public record CreateProductRequest(
    @NotBlank @Size(max = 80) @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "SKU may contain letters, digits, underscores, and hyphens") String sku,
    @NotBlank @Size(max = 180) String name,
    @Size(max = 120) String barcode,
    @NotNull UUID categoryId,
    @NotBlank @Size(max = 20) String unitOfMeasure,
    @NotNull @PositiveOrZero BigDecimal costPrice,
    @NotNull @PositiveOrZero BigDecimal sellingPrice,
    @NotNull @PositiveOrZero BigDecimal minimumStock,
    @NotNull @PositiveOrZero BigDecimal reorderLevel,
    @NotNull @PositiveOrZero BigDecimal maximumStock) { }
