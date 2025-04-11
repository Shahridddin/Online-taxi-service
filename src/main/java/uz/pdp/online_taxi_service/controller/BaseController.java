package uz.pdp.online_taxi_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.online_taxi_service.entity.AuthUser;
import uz.pdp.online_taxi_service.entity.Driver;
import uz.pdp.online_taxi_service.repository.AuthUserRepository;
import uz.pdp.online_taxi_service.repository.DriverRepository;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BaseController {

    private final DriverRepository driverRepository;
    private final AuthUserRepository authUserRepository;

    @GetMapping("/auth/active/drivers")
    public String drivers(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Page<Driver> allActiveDrivers = driverRepository.findAllActiveDrivers(pageable);
        model.addAttribute("drivers", allActiveDrivers.getContent());
        model.addAttribute("totalPages", allActiveDrivers.getTotalPages());
        model.addAttribute("currentPage", page);
        return "active driver";
    }


    @PostMapping("/auth/active/driver/block/{id}")
    public String block(@PathVariable UUID id) {
        Optional<Driver> currentDriver = driverRepository.findById(id);
        if (currentDriver.isPresent()) {
            Driver driver = currentDriver.get();
            driver.setActive(false);
            driverRepository.save(driver);
        }
        return "redirect:/auth/active/drivers";
    }

    @PostMapping("/auth/active/driver/delete/{id}")
    public String delete(@PathVariable UUID id) {
        driverRepository.deleteById(id);
        return "redirect:/auth/active/drivers";
    }

    @GetMapping("/auth/inactive/drivers")
    public String inActiveDrivers(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Page<Driver> allActiveDrivers = driverRepository.findAllInActiveDrivers(pageable);
        model.addAttribute("drivers", allActiveDrivers.getContent());
        model.addAttribute("totalPages", allActiveDrivers.getTotalPages());
        model.addAttribute("currentPage", page);
        return "in active driver";
    }


    @PostMapping("/auth/inactive/driver/inBlock/{id}")
    public String inBlock(@PathVariable UUID id) {
        Optional<Driver> currentUser = driverRepository.findById(id);
        if (currentUser.isPresent()) {
            Driver driver = currentUser.get();
            driver.setActive(true);
            driverRepository.save(driver);
        }
        return "redirect:/auth/inactive/drivers";
    }

    @PostMapping("/auth/inactive/driver/delete/{id}")
    public String inActiveDriverDelete(@PathVariable UUID id) {
        driverRepository.deleteById(id);
        return "redirect:/auth/inactive/drivers";
    }

    @GetMapping("/auth/active/users")
    public String users(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Page<AuthUser> users = authUserRepository.findAllActiveUsers(pageable);

        model.addAttribute("users", users.getContent());
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("currentPage", page);
        return "active user";
    }

    @PostMapping("/auth/active/user/delete/{id}")
    public String deleteUser(@PathVariable UUID id) {
        authUserRepository.deleteById(id);
        return "redirect:/auth/active/drivers";
    }

    @PostMapping("/auth/active/user/block/{id}")
    public String blockUser(@PathVariable UUID id) {
        Optional<AuthUser> currentUser = authUserRepository.findById(id);
        if (currentUser.isPresent()) {
            AuthUser user = currentUser.get();
            user.setActive(false);
            authUserRepository.save(user);
        }
        return "redirect:/auth/active/drivers";
    }

    @GetMapping("/auth/inactive/users")
    public String inActiveUsers(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Page<AuthUser> users = authUserRepository.findAllInActiveUsers(pageable);

        model.addAttribute("users", users.getContent());
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("currentPage", page);
        return "in active user";
    }

    @PostMapping("/auth/inactive/user/delete/{id}")
    public String inActiveUserDelete(@PathVariable UUID id) {
        authUserRepository.deleteById(id);
        return "redirect:/auth/inactive/drivers";
    }

    @PostMapping("/auth/inactive/user/inBlock/{id}")
    public String inBlockUser(@PathVariable UUID id) {
        Optional<AuthUser> currentUser = authUserRepository.findById(id);
        if (currentUser.isPresent()) {
            AuthUser user = currentUser.get();
            user.setActive(true);
            authUserRepository.save(user);
        }
        return "redirect:/auth/inactive/drivers";
    }
}
