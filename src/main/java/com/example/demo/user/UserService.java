package com.example.demo.user;

import com.example.demo.common.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Member getUserInfo(String email){
        return userRepository.findById(email).orElseThrow();
    }
}
