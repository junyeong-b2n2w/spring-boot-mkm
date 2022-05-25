package com.example.demo.common;

import com.example.demo.common.dto.JwtRequestDto;
import com.example.demo.common.dto.JwtResponseDto;
import com.example.demo.common.dto.MemberSignupRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public JwtResponseDto login(@RequestBody JwtRequestDto request) {
        try {
            return authService.login(request);
        } catch (Exception e) {
            return new JwtResponseDto(e.getMessage());
        }
    }


    @GetMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
    public JwtResponseDto user(HttpServletRequest request) {
        try {
            return authService.user(request);
        } catch (Exception e) {
            return new JwtResponseDto(e.getMessage());
        }
    }


    @PostMapping(value = "signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public String signup(@RequestBody MemberSignupRequestDto request) {
        return authService.signup(request);
    }
}
