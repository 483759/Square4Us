package com.ssafy.square4us;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.model.repository.MemberRepository;
import com.ssafy.square4us.api.mvc.service.MemberService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Rollback(false)
class LoginTest {
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberRepository memberRepo;

	@Test
	public void login() {
		String email = "1234";
		String pwd = "1234";

		Member member = memberService.getMemberByEmail(email);
		//Optional<Member> member = memberRepo.findByEmail(email);

		if (member!=null) {
			System.out.println(member);
			assertEquals(member.getEmail(), "1234");
		}else {
			assertFalse(false);
		}
	}
	
	@Transactional
	@Test
	public void join() {
		String email="ssafy";
		String pwd="0000";
		
		
	}

}
