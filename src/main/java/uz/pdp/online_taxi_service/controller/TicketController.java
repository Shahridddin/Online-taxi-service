package uz.pdp.online_taxi_service.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.online_taxi_service.dto.TicketCreateDto;
import uz.pdp.online_taxi_service.dto.TicketSearchDto;
import uz.pdp.online_taxi_service.entity.Driver;
import uz.pdp.online_taxi_service.entity.Ticket;
import uz.pdp.online_taxi_service.repository.DriverRepository;
import uz.pdp.online_taxi_service.repository.TicketRepository;
import uz.pdp.online_taxi_service.service.TicketService;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class TicketController {

    private final DriverRepository driverRepository;
    private final TicketService ticketService;
    private final TicketRepository ticketRepository;

    @GetMapping("/auth/ticket/{id}")
    public String ticket(@PathVariable UUID id, HttpSession session) {
        session.setAttribute("driver_id", id);
        return "add ticket";
    }

    @PostMapping("/auth/ticket/create")
    public String createTicket(@ModelAttribute TicketCreateDto dto, HttpSession session) {
        UUID id = (UUID) session.getAttribute("driver_id");
        Optional<Driver> currentDriver = driverRepository.findById(id);
        if (currentDriver.isPresent()) {
            Ticket ticket = new Ticket();
            ticket.setDriver(currentDriver.get());
            ticketService.create(dto, id);
        }
        session.removeAttribute("driver_id");
        return "redirect:/auth/active/drivers";
    }

    @GetMapping("/auth/ticket/list")
    public String ticketList(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Ticket> tickets = ticketRepository.findAllTickets(pageable);

        model.addAttribute("tickets", tickets.getContent());
        model.addAttribute("totalPages", tickets.getTotalPages());
        model.addAttribute("currentPage", page);
        return "ticket list";
    }

    @PostMapping("/auth/ticket/delete/{id}")
    public String deleteTicket(@PathVariable UUID id) {
        ticketRepository.deleteById(id);
        return "redirect:/auth/ticket/list";
    }


    @GetMapping("/user/search/list")
    public String search(@RequestParam(value = "startLocation",required = false) String startLocation,
                         @RequestParam(value = "endLocation",required = false) String endLocation,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         Model model) {

        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Page<Ticket> tickets = ticketRepository.searchTicket(startLocation, endLocation, pageable);

        model.addAttribute("tickets", tickets.getContent());
        model.addAttribute("totalPages", tickets.getTotalPages());
        model.addAttribute("currentPage", page);

        return "user page";
    }

}
