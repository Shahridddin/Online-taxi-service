package uz.pdp.online_taxi_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.online_taxi_service.entity.AuthUser;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, UUID> {
    Optional<AuthUser> findByEmail(String email);

    Optional<AuthUser> findByUsername(String username);

    @Query("SELECT u from AuthUser u where u.isActive = true ")
    Page<AuthUser> findAllActiveUsers(Pageable pageable);

    @Query("SELECT u from AuthUser u where u.isActive = false ")
    Page<AuthUser> findAllInActiveUsers(Pageable pageable);
}