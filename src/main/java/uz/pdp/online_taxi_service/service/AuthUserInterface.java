package uz.pdp.online_taxi_service.service;

import uz.pdp.online_taxi_service.dto.AuthUserCreateDto;

public interface AuthUserInterface {
    void create(AuthUserCreateDto dto);
}
