package uz.pdp.online_taxi_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.online_taxi_service.entity.Driver;

import java.util.UUID;

@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {

    @Query("SELECT u from Driver u where u.isActive = true")
    Page<Driver> findAllActiveDrivers(Pageable pageable);

    @Query("SELECT u from Driver u where u.isActive = false ")
    Page<Driver> findAllInActiveDrivers(Pageable pageable);
}