package com.soumik.authservice.Service;

import com.soumik.authservice.DTO.JWTResponse;
import com.soumik.authservice.DTO.LoginRequest;
import com.soumik.authservice.DTO.RegistrationRequest;
import com.soumik.authservice.DTO.UserResponse;
import com.soumik.authservice.InvalidCredentialException;
import com.soumik.authservice.Util.JWTUtil;
import com.soumik.authservice.Model.User;
import com.soumik.authservice.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    final
    PasswordEncoder passwordEncoder;

    final
    JWTUtil jwtUtil;

    final
    UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public boolean isValidUser(String email) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getIsEnable();
    }

    public User getUserDetails(String email) {
        return userRepository.findByEmail(email);
    }

    public JWTResponse getLoginToken(LoginRequest loginRequest) throws InvalidCredentialException {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()) && user.getIsEnable()){
            String token = jwtUtil.createToken(user);
            return JWTResponse.builder()
                    .token(token)
                    .build();
        } else {
            throw new InvalidCredentialException();
        }

    }


    public UserResponse createNewUser(RegistrationRequest registrationRequest) {

        User user = userRepository.findByEmail(registrationRequest.getEmail());

        if(user == null) {

            String password = passwordEncoder.encode(registrationRequest.getPassword());

            User newUser = User.builder()
                    .firstName(registrationRequest.getFirstName().trim())
                    .lastName(registrationRequest.getLastName().trim())
                    .email(registrationRequest.getEmail().trim())
                    .password(password)
                    .isEnable(true)
                    .build();

            User savedUser = userRepository.save(newUser);

            return UserResponse.builder()
                    .id(savedUser.getId())
                    .firstName(savedUser.getFirstName())
                    .lastName(savedUser.getLastName())
                    .email(savedUser.getEmail())
                    .build();
        } else {
            throw new RuntimeException();
        }
    }
}
