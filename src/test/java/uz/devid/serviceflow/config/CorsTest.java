package uz.devid.serviceflow.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class CorsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void cors_ShouldAllowAnyOriginWithCredentials() throws Exception {
        String testOrigin = "http://localhost:3000";

        mockMvc.perform(options("/api/flows")
                .header("Access-Control-Request-Method", "GET")
                .header("Origin", testOrigin))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", testOrigin))
                .andExpect(header().string("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"))
                .andExpect(header().string("Access-Control-Allow-Credentials", "true"));
    }
}
