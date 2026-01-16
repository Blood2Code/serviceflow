package uz.devid.serviceflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.devid.serviceflow.entity.Request;

import java.util.Optional;
import java.util.UUID;

public interface RequestRepository extends JpaRepository<Request, UUID> {
    Optional<Request> findByCorrelationId(String correlationId);
}
