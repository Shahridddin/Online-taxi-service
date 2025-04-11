package uz.pdp.online_taxi_service.dto;

import java.time.LocalDateTime;

public record TicketCreateDto(
        String startLocation,
        String endLocation,
        LocalDateTime startDate,
        LocalDateTime endDate,
        double fare
) {
}
