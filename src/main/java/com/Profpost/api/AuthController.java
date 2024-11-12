package com.Profpost.api;

import com.Profpost.dto.*;
import com.Profpost.service.CreatorService;
import com.Profpost.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Profpost.dto.UserIdRequestDTO;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")

public class AuthController {
    private final UserService userService;
    private final CreatorService creatorService;

    // Endpoint para registrar lector
    @PostMapping("/register/reader")
    public ResponseEntity<UserProfileDTO> registerReader(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserProfileDTO userProfile = userService.registerReader(userRegistrationDTO);
        return new ResponseEntity<>(userProfile, HttpStatus.CREATED);
    }

    // Endpoint para registrar creador
    @PostMapping("/register/creator")
    public ResponseEntity<UserProfileDTO> registerCreator(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserProfileDTO userProfile = userService.registerCreator(userRegistrationDTO);
        return new ResponseEntity<>(userProfile, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        AuthResponseDTO authResponse = userService.login(loginDTO);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @GetMapping("/creator-id")
    public ResponseEntity<Integer> getCreatorId(@RequestParam Integer userId) {
        Integer creatorId = creatorService.findCreatorIdByUserId(userId);
        return ResponseEntity.ok(creatorId);
    }

}
