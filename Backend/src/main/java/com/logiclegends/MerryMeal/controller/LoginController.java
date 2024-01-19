package com.logiclegends.MerryMeal.controller;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 

import com.logiclegends.MerryMeal.JwtSecurity.AuthenticationRequest;
import com.logiclegends.MerryMeal.JwtSecurity.AuthenticationResponse;
import com.logiclegends.MerryMeal.entities.UserRole;
import com.logiclegends.MerryMeal.service.JwtUtil;
import com.logiclegends.MerryMeal.service.RateLimitingService;
import com.logiclegends.MerryMeal.service.RegistrationServiceImpl;
import com.logiclegends.MerryMeal.service.UserPrincipal;

 

@RestController
@RequestMapping("/logic/auth")
public class LoginController {

 

    @Autowired
    private AuthenticationManager authenticationManager;

 

    @Autowired
    private RegistrationServiceImpl userDetailsService;

 

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RateLimitingService rateLimitingService;

 

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        String username = authenticationRequest.getEmail();

        if (rateLimitingService.hasExceededMaxLoginAttempts(username)) {
            if (!rateLimitingService.canLoginAfterWaitPeriod(username)) {
                long waitTime = rateLimitingService.getRemainingWaitTimeSeconds(username);

                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("You have exceeded the maximum login attempts. Please wait for " + waitTime + " seconds.");
            }
            // Reset login attempts if the waiting period is over
            rateLimitingService.resetLoginAttempts(username);
        }

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, authenticationRequest.getPassword())
            );
            // Successful login, reset login attempts
            rateLimitingService.resetLoginAttempts(username);
        } catch (BadCredentialsException e) {
            // Failed login attempt, register it with the rate limiting service
            rateLimitingService.registerFailedLoginAttempt(username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Incorrect email or password. Please check your credentials and try again.");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String jwt = jwtUtil.generateToken(userDetails);

        final UserPrincipal userPrincipal = (UserPrincipal) userDetails;
        final UserRole role = userPrincipal.getRole();

        return ResponseEntity.ok(new AuthenticationResponse(jwt, role.toString()));
    }

 

//    @PostMapping("/authenticate")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
//            );
//        } catch (BadCredentialsException e) {
//            throw new RuntimeException("Incorrect email or password");
//        }
//
//        // Assuming your UserDetailsService implementation can also load users by email.
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail()); 
//        final String jwt = jwtUtil.generateToken(userDetails);
//        
//        //Cast UserDetails to UserPrincipal to access the getRole method
//        final UserPrincipal userPrincipal = (UserPrincipal) userDetails;
//        final UserRole role = userPrincipal.getRole();
//
//        return ResponseEntity.ok(new AuthenticationResponse(jwt,role.toString() ));
//    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String adminLogin() {

    	return "Admin Successfully Logged In!";
    }

    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/member")
    public String memberLogin() {

    	return "Member Successfully Logged In!";
    }


//    @PreAuthorize("hasRole('ROLE_VOLUNTEER')")
    @GetMapping("/volunteer")
    public String volunteerLogin() {

    	return "Volunteer Successfully Logged In!";
    }

    @PreAuthorize("hasRole('ROLE_PARTNER')")
    @GetMapping("/partner")
    public String partnerLogin() {

    	return "Partner Successfully Logged In!";
    }




}