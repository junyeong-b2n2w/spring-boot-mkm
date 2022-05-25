package com.example.demo.user.dto;

import com.example.demo.common.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String name;
    private String email;
    private Role role;
}
