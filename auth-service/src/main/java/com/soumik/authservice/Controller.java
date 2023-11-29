package com.soumik.authservice;

import com.soumik.authservice.DTO.JWTResponse;
import com.soumik.authservice.DTO.LoginRequest;
import com.soumik.authservice.DTO.RegistrationRequest;
import com.soumik.authservice.DTO.UserResponse;
import com.soumik.authservice.Service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Service API")
public class Controller {

    final
    UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponse> createNewUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        UserResponse newUser = userService.createNewUser(registrationRequest);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody @Valid LoginRequest loginRequest) throws InvalidCredentialException {
        JWTResponse loginToken = userService.getLoginToken(loginRequest);
        return new ResponseEntity<>(loginToken, HttpStatus.OK);
    }

//    @GetMapping("/validate")
//    public void validate() {
//
//
//    }


}
