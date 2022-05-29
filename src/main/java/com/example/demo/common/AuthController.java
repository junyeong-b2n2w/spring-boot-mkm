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

import java.util.HashMap;
import java.util.Map;

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
    public  ResponseEntity<Map<String,Object>> getMyMemberInfo() {
        Member member = authService.getMyMemberWithAuthorities().get();
        member.removePasword();
        Map<String,Object> map =  new HashMap<String, Object>();
        map.put("user", member);
        return ResponseEntity.ok(map);
    }

    @GetMapping(value = "user/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> getMemberInfo(@PathVariable String email) {
        Member member = authService.getMemberWithAuthorities(email).get();
        member.removePasword();
        Map<String,Object> map =  new HashMap<String, Object>();
        map.put("user", member);
        return ResponseEntity.ok(map);
    }

    @PostMapping(value = "signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public String signup(@RequestBody MemberSignupRequestDto request) {
        return authService.signup(request);
    }
}
