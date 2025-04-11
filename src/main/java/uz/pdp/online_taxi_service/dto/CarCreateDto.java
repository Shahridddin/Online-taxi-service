package uz.pdp.online_taxi_service.dto;

public record CarCreateDto(
        String model,
        int seatCount,
        String color,
        String carNumber
) {
}
