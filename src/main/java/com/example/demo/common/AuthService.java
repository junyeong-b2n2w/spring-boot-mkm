package com.example.demo.common;

import com.example.demo.common.domain.Member;
import com.example.demo.common.dto.JwtRequestDto;
import com.example.demo.common.dto.JwtResponseDto;
import com.example.demo.common.dto.MemberSignupRequestDto;
import com.example.demo.common.security.JwtTokenProvider;
import com.example.demo.common.security.UserDetailsImpl;

import com.example.demo.common.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtResponseDto login(JwtRequestDto request) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        return createJwtToken(authentication);
    }

    private JwtResponseDto createJwtToken(Authentication authentication){
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(principal);
        return new JwtResponseDto(token);
    }

    public String signup(MemberSignupRequestDto request) {
        boolean existMember = memberRepository.existsById(request.getEmail());

        // 이미 회원이 존재하는 경우
        if (existMember) return null;

        Member member = new Member(request);
        member.encryptPassword(passwordEncoder);

        memberRepository.save(member);
        return member.getEmail();
    }


    public Optional<Member> getMemberWithAuthorities(String email){
        return memberRepository.findOneWithAuthoritiesByEmail(email);
    }


    public Optional<Member> getMyMemberWithAuthorities(){
        return SecurityUtil.getCurrentUsername().flatMap(memberRepository::findOneWithAuthoritiesByEmail);
    }

}