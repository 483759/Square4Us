package com.ssafy.square4us.api.mvc.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.square4us.api.mvc.model.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	public Optional<Member> findByEmail(String email);
}
