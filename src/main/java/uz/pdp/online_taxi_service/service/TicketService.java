package uz.pdp.online_taxi_service.service;

import org.springframework.stereotype.Service;
import uz.pdp.online_taxi_service.dto.TicketCreateDto;
import uz.pdp.online_taxi_service.entity.Driver;
import uz.pdp.online_taxi_service.entity.Ticket;
import uz.pdp.online_taxi_service.repository.DriverRepository;
import uz.pdp.online_taxi_service.repository.TicketRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService implements BaseInterface<TicketCreateDto, UUID> {

    private final TicketRepository ticketRepository;
    private final DriverRepository driverRepository;

    public TicketService(TicketRepository ticketRepository, DriverRepository driverRepository) {
        this.ticketRepository = ticketRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public void create(TicketCreateDto dto, UUID currentDriverId) {

        Ticket ticket = new Ticket();

        Optional<Driver> byId = driverRepository.findById(currentDriverId);

        if (byId.isPresent()) {
            Driver driver = byId.get();
            ticket.setDriver(driver);
        }

        ticket.setStartLocation(dto.startLocation());
        ticket.setEndLocation(dto.endLocation());
        ticket.setStartDate(dto.startDate());
        ticket.setEndDate(dto.endDate());
        ticket.setFare(dto.fare());

        ticketRepository.save(ticket);
    }
}
