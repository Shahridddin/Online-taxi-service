package uz.pdp.online_taxi_service.dto;

public record AuthUserCreateDto(
        String email,
        String username,
        String password,
        String firstName,
        String lastName,
        String phoneNumber
) {
}
