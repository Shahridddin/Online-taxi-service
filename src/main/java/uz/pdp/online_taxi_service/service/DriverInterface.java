package uz.pdp.online_taxi_service.service;

import uz.pdp.online_taxi_service.dto.CarCreateDto;
import uz.pdp.online_taxi_service.dto.DriverCreateDto;

public interface DriverInterface {

    void createDriver(DriverCreateDto dto, CarCreateDto carDto);
}
