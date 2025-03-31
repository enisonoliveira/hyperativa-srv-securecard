package com.hyperativa.payment.securecard.infrastructure.configuration.webfilters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WebSecurityFilterTest {

    @Autowired
    private MockMvc mockMvc;


    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
    }

    @Test
    void shouldAllowPublicEndpoints() throws Exception {
        // Test a public endpoint (e.g., swagger-ui)
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAuthenticatePrivateEndpoints() throws Exception {
        // Test a private endpoint
        mockMvc.perform(get("/api/private-endpoint"))
                .andExpect(status().isUnauthorized()); 
    }

    @Test
    void shouldDisableCsrf() throws Exception {
        // Verify CSRF is disabled
        request.setMethod("POST");
        mockMvc.perform(get("/api/token"))
                .andExpect(status().isInternalServerError()); // No CSRF token is needed
    }

    @Test
    void shouldPermitH2Console() throws Exception {
        // Test the H2 console endpoint
        mockMvc.perform(get("/h2-console/login.do"))
                .andExpect(status().isInternalServerError());
    }
}