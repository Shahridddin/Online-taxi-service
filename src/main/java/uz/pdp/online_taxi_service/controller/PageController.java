package uz.pdp.online_taxi_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/admin/page")
    public String adminPage() {
        return "admin page";
    }

    @GetMapping("/user/page")
    public String userPage() {
        return "user page";
    }

}
