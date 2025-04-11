package uz.pdp.online_taxi_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.online_taxi_service.dto.CarCreateDto;
import uz.pdp.online_taxi_service.dto.DriverCreateDto;
import uz.pdp.online_taxi_service.entity.Car;
import uz.pdp.online_taxi_service.entity.Driver;
import uz.pdp.online_taxi_service.repository.CarRepository;
import uz.pdp.online_taxi_service.repository.DriverRepository;

@Service
public class DriverService implements DriverInterface {
    private final PasswordEncoder passwordEncoder;
    private final DriverRepository driverRepository;
    private final CarRepository carRepository;

    public DriverService(PasswordEncoder passwordEncoder, DriverRepository driverRepository, CarRepository carRepository) {
        this.passwordEncoder = passwordEncoder;
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }

    @Override
    public void createDriver(DriverCreateDto dto, CarCreateDto carDto) {
        Driver driver = new Driver();
        driver.setEmail(dto.email());
        driver.setUsername(dto.username());
        driver.setPassword(passwordEncoder.encode(dto.password()));
        driver.setFirstName(dto.firstName());
        driver.setLastName(dto.lastName());
        driver.setAge(dto.age());
        driver.setAddress(dto.address());
        driver.setPhoneNumber(dto.phoneNumber());

        driverRepository.save(driver);

        Car car = new Car();
        car.setModel(carDto.model());
        car.setSeatCount(carDto.seatCount());
        car.setColor(carDto.color());
        car.setCarNumber(carDto.carNumber());
        car.setDriver(driver);
        driver.setCar(car);
        carRepository.save(car);
    }
}
