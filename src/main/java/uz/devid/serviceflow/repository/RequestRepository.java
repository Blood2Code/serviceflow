package uz.devid.serviceflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.devid.serviceflow.entity.Request;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<Request, UUID> {
    @Query(value = "SELECT * FROM request WHERE destination_service LIKE :destination_service ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    Optional<Request> findLastByRequestPath(@Param("destination_service") String destinationService);
}
