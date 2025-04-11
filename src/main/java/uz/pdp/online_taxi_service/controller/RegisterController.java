package uz.pdp.online_taxi_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uz.pdp.online_taxi_service.dto.AuthUserCreateDto;
import uz.pdp.online_taxi_service.dto.CarCreateDto;
import uz.pdp.online_taxi_service.dto.DriverCreateDto;
import uz.pdp.online_taxi_service.entity.Driver;
import uz.pdp.online_taxi_service.service.AuthUserService;
import uz.pdp.online_taxi_service.service.DriverService;

@Controller
public class RegisterController {

    private final AuthUserService authUserService;
    private final DriverService driverService;

    public RegisterController(AuthUserService authUserService, DriverService driverService) {
        this.authUserService = authUserService;
        this.driverService = driverService;
    }

    @GetMapping("/auth/register")
    public String register() {
        return "register";
    }

    @PostMapping("/auth/create")
    public String create(@ModelAttribute AuthUserCreateDto dto) {
        authUserService.create(dto);
        return "redirect:/auth/login";
    }

    @GetMapping("/auth/driver/register")
    public String registerDriver() {
        return "register driver";
    }

    @PostMapping("/auth/driver/create")
    public String createDriver(@ModelAttribute DriverCreateDto dto, @ModelAttribute CarCreateDto car) {
        driverService.createDriver(dto, car);
        return "redirect:/admin/page";
    }
}
