package com.rohit.inventory.service;

import static org.assertj.core.api.Assertions.assertThat; import static org.mockito.BDDMockito.given;
import com.rohit.inventory.mapper.ProductMapper; import com.rohit.inventory.repository.ProductRepository; import java.util.List; import org.junit.jupiter.api.Test; import org.junit.jupiter.api.extension.ExtendWith; import org.mockito.InjectMocks; import org.mockito.Mock; import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class) class ProductServiceTest {
    @Mock ProductRepository repository; @Mock ProductMapper mapper; @InjectMocks ProductService service;
    @Test void returnsMappedProductList() { given(repository.findAll()).willReturn(List.of()); assertThat(service.findAll()).isEmpty(); }
}
