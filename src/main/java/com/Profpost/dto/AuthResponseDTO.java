package com.Profpost.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private String name;
    private String role;
}
