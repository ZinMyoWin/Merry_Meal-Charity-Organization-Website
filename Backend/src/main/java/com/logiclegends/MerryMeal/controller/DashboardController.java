package com.logiclegends.MerryMeal.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logic")
public class DashboardController {

	@GetMapping("/dashboard")
    private String userDashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Retrieve the username or any other user details from the authentication object
        String username = authentication.getName();

        // You can also get other user information as needed
        // For example: UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // String email = userDetails.getEmail();

        return "Information Retrieved for user: " + username;
    }
}
