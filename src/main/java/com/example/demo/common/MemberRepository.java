package com.example.demo.common;

import com.example.demo.common.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    @EntityGraph(attributePaths = "role")
    Optional<Member> findOneWithAuthoritiesByEmail(String email);
}
