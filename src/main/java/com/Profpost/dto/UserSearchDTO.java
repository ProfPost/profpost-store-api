package com.Profpost.dto;

import com.Profpost.model.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSearchDTO {
    private Integer id;
    private String name;
    private ERole role;
}
