package com.example.demo.common;

import com.example.demo.common.domain.Member;
import com.example.demo.common.dto.JwtRequestDto;
import com.example.demo.common.dto.JwtResponseDto;
import com.example.demo.common.dto.MemberSignupRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Member> getMyMemberInfo() {
        Member member = authService.getMyMemberWithAuthorities().get();
        member.removePasword();
        return ResponseEntity.ok(member);
    }

    @GetMapping(value = "user/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Member> getMemberInfo(@PathVariable String email) {
        Member member = authService.getMemberWithAuthorities(email).get();
        member.removePasword();
        return ResponseEntity.ok(member);
    }

    @PostMapping(value = "signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public String signup(@RequestBody MemberSignupRequestDto request) {
        return authService.signup(request);
    }
}
