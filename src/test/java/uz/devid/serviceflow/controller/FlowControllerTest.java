package uz.devid.serviceflow.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import uz.devid.serviceflow.entity.Request;
import uz.devid.serviceflow.repository.RequestRepository;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import org.springframework.context.annotation.Import;
import uz.devid.serviceflow.config.SecurityConfig;

@WebMvcTest(FlowController.class)
@Import(SecurityConfig.class)
public class FlowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestRepository requestRepository;

    @Test
    public void getAllFlows_ShouldReturnList() throws Exception {
        Request request1 = Request.builder()
                .correlationId("corr-1")
                .sourceService("service-a")
                .destinationService("service-b")
                .requestPath("/api/test")
                .domainPath("test-domain")
                .httpMethod("GET")
                .build();

        Request request2 = Request.builder()
                .correlationId("corr-2")
                .sourceService("service-c")
                .destinationService("service-d")
                .requestPath("/api/demo")
                .domainPath("demo-domain")
                .httpMethod("POST")
                .build();

        List<Request> requests = Arrays.asList(request1, request2);

        Mockito.when(requestRepository.findAll()).thenReturn(requests);

        mockMvc.perform(get("/api/flows"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].correlationId", is("corr-1")))
                .andExpect(jsonPath("$[1].correlationId", is("corr-2")));
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createFlow_ShouldReturnSavedRequest() throws Exception {
        Request request = Request.builder()
                .correlationId("corr-new")
                .sourceService("service-new")
                .destinationService("service-dest")
                .requestPath("/api/new")
                .domainPath("new-domain")
                .httpMethod("POST")
                .build();

        Mockito.when(requestRepository.save(Mockito.any(Request.class))).thenReturn(request);

        mockMvc.perform(post("/api/flows")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correlationId", is("corr-new")))
                .andExpect(jsonPath("$.sourceService", is("service-new")));
    }
}
