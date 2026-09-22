package com.rohit.inventory.service;
import com.rohit.inventory.dto.response.ProductResponse; import com.rohit.inventory.mapper.ProductMapper; import com.rohit.inventory.repository.ProductRepository;
import java.util.List; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service @Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository repository; private final ProductMapper mapper;
    public ProductService(ProductRepository repository, ProductMapper mapper) { this.repository = repository; this.mapper = mapper; }
    public List<ProductResponse> findAll() { return repository.findAll().stream().map(mapper::toResponse).toList(); }
}
