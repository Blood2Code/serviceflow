package uz.devid.serviceflow.entity;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID UUID;

    @Column(name = "source_service", nullable = false)
    private String sourceService;

    @Column(name = "destination_service", nullable = false)
    private String destinationService;

    @Column(name = "request_path", nullable = false)
    private String requestPath;

    @Column(name = "domain_path", nullable = false)
    private String domainPath;

    @Column(name = "http_method", nullable = false)
    private String httpMethod;

    @Column(name = "status_code")
    private Integer statusCode;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "request_payload", columnDefinition = "TEXT")
    private String requestPayload;

    @Column(name = "response_payload", columnDefinition = "TEXT")
    private String responsePayload;
}
