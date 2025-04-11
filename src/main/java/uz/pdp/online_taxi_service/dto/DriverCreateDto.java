package uz.pdp.online_taxi_service.dto;


public record DriverCreateDto(
        String email,
        String username,
        String password,
        String firstName,
        String lastName,
        int age,
        String address,
        String phoneNumber
) {
}
