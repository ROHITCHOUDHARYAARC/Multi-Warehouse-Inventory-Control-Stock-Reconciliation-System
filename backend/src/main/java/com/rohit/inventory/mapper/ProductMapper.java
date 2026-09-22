package com.rohit.inventory.mapper;
import com.rohit.inventory.dto.response.ProductResponse; import com.rohit.inventory.entity.Product; import org.springframework.stereotype.Component;
@Component public class ProductMapper { public ProductResponse toResponse(Product product) { return new ProductResponse(product.getId(), product.getSku(), product.getName(), product.getBarcode(), product.getCategory().getId(), product.getCategory().getName(), product.getStatus()); } }
