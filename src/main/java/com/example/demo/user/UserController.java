package com.example.demo.user;

import com.example.demo.common.domain.Member;
import com.example.demo.user.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto login(@PathVariable(name = "email") String email) {
        Member member = userService.getUserInfo(email);
        UserResponseDto result = new UserResponseDto();
        BeanUtils.copyProperties(member, result);
        return result;
    }
}

