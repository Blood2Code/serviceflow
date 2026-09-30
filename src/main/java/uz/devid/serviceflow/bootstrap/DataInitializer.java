package uz.devid.serviceflow.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.devid.serviceflow.entity.Request;
import uz.devid.serviceflow.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RequestRepository requestRepository;

    @Override
    public void run(String... args) throws Exception {
        if (requestRepository.count() > 0) {
            log.info("Data already exists. Skipping initialization.");
            return;
        }

        log.info("Initializing mock data...");

        Request r1 = Request.builder()
                .correlationId(UUID.randomUUID().toString())
                .sourceService("payment-service")
                .destinationService("user-service")
                .requestPath("/api/users/123")
                .domainPath("users")
                .httpMethod("GET")
                .statusCode(200)
                .timestamp(LocalDateTime.now().minusMinutes(5))
                .requestPayload("{\"userId\": 123}")
                .build();

        Request r2 = Request.builder()
                .correlationId(UUID.randomUUID().toString())
                .sourceService("order-service")
                .destinationService("inventory-service")
                .requestPath("/api/inventory/check")
                .domainPath("inventory")
                .httpMethod("POST")
                .statusCode(200)
                .timestamp(LocalDateTime.now().minusMinutes(4))
                .requestPayload("{\"productId\": \"SKU-999\", \"quantity\": 1}")
                .build();

        Request r3 = Request.builder()
                .correlationId(UUID.randomUUID().toString())
                .sourceService("api-gateway")
                .destinationService("auth-service")
                .requestPath("/oauth/token")
                .domainPath("auth")
                .httpMethod("POST")
                .statusCode(201)
                .timestamp(LocalDateTime.now().minusMinutes(10))
                .requestPayload("{\"grant_type\": \"password\"}")
                .build();

        Request r4 = Request.builder()
                .correlationId(UUID.randomUUID().toString())
                .sourceService("notification-service")
                .destinationService("email-provider")
                .requestPath("/send-email")
                .domainPath("notifications")
                .httpMethod("POST")
                .statusCode(202)
                .timestamp(LocalDateTime.now().minusMinutes(2))
                .requestPayload("{\"to\": \"user@example.com\", \"subject\": \"Welcome\"}")
                .build();

        Request r5 = Request.builder()
                .correlationId(UUID.randomUUID().toString())
                .sourceService("frontend")
                .destinationService("payment-service")
                .requestPath("/api/payments/process")
                .domainPath("payments")
                .httpMethod("POST")
                .statusCode(500)
                .timestamp(LocalDateTime.now().minusSeconds(30))
                .requestPayload("{\"amount\": 100.00, \"currency\": \"USD\"}")
                .build();

        Request r6 = Request.builder()
                .correlationId(UUID.randomUUID().toString())
                .sourceService("frontend")
                .destinationService("product-service")
                .requestPath("/api/products")
                .domainPath("products")
                .httpMethod("GET")
                .statusCode(200)
                .timestamp(LocalDateTime.now().minusHours(1))
                .requestPayload("{}")
                .build();

        requestRepository.saveAll(Arrays.asList(r1, r2, r3, r4, r5, r6));

        log.info("Mock data initialized with {} requests.", requestRepository.count());
    }
}
