package uz.pdp.online_taxi_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.online_taxi_service.entity.Ticket;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    @Query("SELECT  u from Ticket u")
    Page<Ticket> findAllTickets(Pageable pageable);

    @Query("SELECT u FROM Ticket u WHERE u.startLocation = :startLocation AND u.endLocation = :endLocation")
    Page<Ticket> searchTicket(@Param("startLocation") String startLocation,
                              @Param("endLocation") String endLocation,
                              Pageable pageable);


}