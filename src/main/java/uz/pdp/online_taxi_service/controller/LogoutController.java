package uz.pdp.online_taxi_service.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uz.pdp.online_taxi_service.configuration.CustomUserDetails;
import uz.pdp.online_taxi_service.entity.AuthUser;
import uz.pdp.online_taxi_service.repository.AuthUserRepository;

import java.util.Optional;

@Controller
public class LogoutController {


    private final AuthUserRepository authUserRepository;

    public LogoutController(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @GetMapping("/auth/logout/page")
    public String logoutPage(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails != null) {
            model.addAttribute("username", customUserDetails.getUsername());
        }
        return "logout page";
    }

    @GetMapping("/auth/logout")
    public String logout() {
        return "login";
    }

    @PostMapping("/auth/logout/account")
    public String logoutAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails != null) {
            String email = customUserDetails.getEmail();
            Optional<AuthUser> byEmail = authUserRepository.findByEmail(email);
            if (byEmail.isPresent()) {
                AuthUser authUser = byEmail.get();
                authUser.setActive(false);
                authUserRepository.save(authUser);
            }
        }
        return "redirect:/auth/login";
    }
}
