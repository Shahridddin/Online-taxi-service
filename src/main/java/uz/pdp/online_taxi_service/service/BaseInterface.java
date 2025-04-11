package uz.pdp.online_taxi_service.service;

import uz.pdp.online_taxi_service.dto.TicketCreateDto;

public interface BaseInterface<CD, UUID> {
    void create(CD dto, UUID id);
}
