package com.example.demo.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSignupRequestDto {

    private String email;
    private String password;
    private String name;
}