package com.rohit.inventory.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.rohit.inventory.security.*;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
@WebMvcTest(HealthController.class) @Import({SecurityConfig.class, RestAuthenticationEntryPoint.class, JwtAuthenticationFilter.class, JwtService.class})
class HealthControllerTest {
    @Autowired MockMvc mockMvc; @org.springframework.boot.test.mock.mockito.MockBean RestAccessDeniedHandler restAccessDeniedHandler; @org.springframework.boot.test.mock.mockito.MockBean InventoryUserDetailsService inventoryUserDetailsService;
    @Test void returnsPublicHealthResponse() throws Exception { mockMvc.perform(get("/api/v1/health")).andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.status").value("UP")); }
}
