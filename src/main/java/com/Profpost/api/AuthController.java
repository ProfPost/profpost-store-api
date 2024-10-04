package com.Profpost.api;

import com.Profpost.dto.LoginRequestDTO;
import com.Profpost.dto.UserDTO;
import com.Profpost.model.entity.User;
import com.Profpost.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        User user = userService.findByEmail(loginRequestDTO.getEmail());
        if (user != null && userService.checkPassword(loginRequestDTO.getPassword(), user.getPassword())) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Login failed", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO userDTO) {
        UserDTO newUserDTO = userService.registerUser(userDTO);
        return new ResponseEntity<>(newUserDTO, HttpStatus.CREATED);
    }
}
