package com.rohit.inventory.service;
import com.rohit.inventory.dto.response.WarehouseResponse; import com.rohit.inventory.mapper.WarehouseMapper; import com.rohit.inventory.repository.WarehouseRepository;
import java.util.List; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service @Transactional(readOnly = true)
public class WarehouseService {
    private final WarehouseRepository repository; private final WarehouseMapper mapper;
    public WarehouseService(WarehouseRepository repository, WarehouseMapper mapper) { this.repository = repository; this.mapper = mapper; }
    public List<WarehouseResponse> findAll() { return repository.findAll().stream().map(mapper::toResponse).toList(); }
}
