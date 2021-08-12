package com.ssafy.square4us.api.mvc.controller;

import com.ssafy.square4us.api.mvc.model.dto.BasicResponseBody;
import com.ssafy.square4us.api.mvc.model.dto.FileDTO;
import com.ssafy.square4us.api.mvc.model.dto.MemberDTO;
import com.ssafy.square4us.api.mvc.model.dto.ResponseFactory;
import com.ssafy.square4us.api.mvc.model.entity.Member;
import com.ssafy.square4us.api.mvc.service.MemberService;
import com.ssafy.square4us.common.auth.MemberDetails;
import com.ssafy.square4us.common.util.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//@Tag(description = "멤버 API", name = "Member")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일과 패스워드를 입력하여 로그인한다", responses = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "일치하지 않는 비밀번호"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 계정"),
            @ApiResponse(responseCode = "500", description = "서버 오류"),})
    public ResponseEntity<? extends BasicResponseBody> login(
            @Parameter(name = "로그인 정보", required = true) @RequestBody MemberDTO.LoginPostReq loginInfo) {
        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();
        
        try {
            Member member = memberService.getMemberByEmail(email);

            if (member == null) {
                return ResponseFactory.notFound();
            }
            if (new BCryptPasswordEncoder().matches(password, member.getPassword()) == false) {
                return ResponseFactory.unauthorized();
            }
            return ResponseEntity.ok(MemberDTO.LoginPostRes.of(200, "로그인 성공", JwtTokenProvider.generateToken(new MemberDetails(member))));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseFactory.internalServerError();
        }
    }

    @PostMapping("/join")
    @Operation(summary = "회원 가입", description = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.", responses = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "409", description = "중복된 계정 오류"),
            @ApiResponse(responseCode = "503", description = "회원가입 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")})
    public ResponseEntity<? extends BasicResponseBody> register(@RequestBody @Parameter(name = "회원가입 정보", required = true) MemberDTO.JoinPostReq req){

        MemberDTO confirmMember = memberService.getMemberDTOByEmail(req.getEmail());
        if (confirmMember != null) {
            return ResponseFactory.conflict();
        }

        MemberDTO member = memberService.createMember(req);

        if (member == null) {
            return ResponseFactory.serviceUnavailable();

        }
        return ResponseFactory.created();
    }

    @GetMapping("/study/{studyId}")
    @Operation(summary = "스터디 회원 목록 조회", description = "특정 스터디에 가입한 회원의 목록을 조회한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> getMembersByStudyId(@PathVariable Long studyId) {
        List<MemberDTO> list = memberService.getMembersByStudy(studyId);
        if (list == null) {
            return ResponseFactory.noContent();
        }
        return ResponseEntity.ok(MemberDTO.InfosGetRes.of(200, "조회 성공", list));
    }

    @GetMapping("/study/{studyId}/wait")
    @Operation(summary = "스터디 가입 대기 회원 목록 조회", description = "특정 스터디에 가입 신청한 회원의 목록을 조회한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "존재하지 않음")})
    public ResponseEntity<? extends BasicResponseBody> getMembersWaitJoin(@PathVariable Long studyId) {
        List<MemberDTO> list = memberService.getMembersWaitJoin(studyId);
        if (list == null) {
            return ResponseFactory.noContent();
        }
        return ResponseEntity.ok(MemberDTO.InfosGetRes.of(200, "조회 성공", list));
    }


    @GetMapping("/me")
    @Operation(summary = "회원 본인 정보 조회", description = "로그인한 회원 본인의 정보를 응답한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "사용자 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")})
    public ResponseEntity<? extends BasicResponseBody> getUserInfo(
            @Parameter(hidden = true) Authentication authentication) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

        if (memberDetails == null) {
            return ResponseFactory.unauthorized();
        }

        String email = memberDetails.getUsername();
        MemberDTO member = memberService.getMemberDTOByEmail(email);

        return ResponseEntity.ok(MemberDTO.InfoGetRes.of(200, "회원 정보 조회 성공", member.getEmail(), member.getRole(), member.getNickname(), member.getProfile(), member.getReport()));
    }

    @PatchMapping("/me")
    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")})
    public ResponseEntity<? extends BasicResponseBody> modifyUserInfo(
            @Parameter(hidden = true) Authentication authentication,
            @RequestBody @Parameter(name = "회원 정보 수정", required = true) MemberDTO.UpdatePatchReq updateInfo) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

        if (memberDetails == null) {
            return ResponseFactory.unauthorized();
        }

        String email = memberDetails.getUsername();
        MemberDTO member = memberService.getMemberDTOByEmail(email);
        memberService.updateMemberByEmail(member.getId(), updateInfo);

        MemberDTO modified = memberService.getMemberDTOByEmail(email);
        return ResponseEntity.ok(MemberDTO.InfoGetRes.of(200, "수정 성공", modified.getEmail(), modified.getRole(), modified.getNickname(), modified.getProfile(), modified.getReport()));
    }

    @PostMapping("/me/profile")
    @Operation(summary = "프로필 사진 변경", description = "회원의 프로필 사진을 변경한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")})
    public ResponseEntity<? extends BasicResponseBody> modifyProfile(@Parameter(hidden = true) Authentication authentication,
                                                                     @Parameter(name = "프로필 사진", required = true) MultipartFile profile) {


        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

        if (memberDetails == null) {
            return ResponseFactory.unauthorized();
        }

        if(profile == null) {
            return ResponseFactory.forbidden();
        }

        String email = memberDetails.getUsername();
        MemberDTO member = null;
        try {
            member = memberService.updateProfileByEmail(email, profile);
        } catch (IOException e) {
            return ResponseFactory.internalServerError();
        }

        if(member == null) {
            return ResponseFactory.serviceUnavailable();
        }

        return ResponseEntity.ok(MemberDTO.InfoGetRes.of(200, "프로필 수정 성공", member.getEmail(), member.getRole(), member.getNickname(), member.getProfile(), member.getReport()));
    }

    @DeleteMapping("/me/profile")
    @Operation(summary = "프로필 사진 삭제", description = "회원의 프로필 사진을 삭제한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")})
    public ResponseEntity<? extends BasicResponseBody> deleteProfile(@Parameter(hidden = true) Authentication authentication) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

        if (memberDetails == null) {
            return ResponseFactory.unauthorized();
        }

        String email = memberDetails.getUsername();
        MemberDTO member = memberService.deleteProfileByEmail(email);

        if(member == null) {
            return ResponseFactory.serviceUnavailable();
        }

        return ResponseEntity.ok(MemberDTO.InfoGetRes.of(200, "프로필 삭제 성공", member.getEmail(), member.getRole(), member.getNickname(), member.getProfile(), member.getReport()));
    }

    @DeleteMapping("me")
    @Operation(summary = "회원 탈퇴", description = "회원의 정보를 삭제한다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")})
    public ResponseEntity<? extends BasicResponseBody> deleteUserInfo(
            @Parameter(hidden = true) Authentication authentication) {
        if (authentication == null) {
            return ResponseFactory.forbidden();
        }

        MemberDetails memberDetails = (MemberDetails) authentication.getDetails();

        if (memberDetails == null) {
            return ResponseFactory.unauthorized();
        }

        boolean flag = memberService.deleteMemberByEmail(memberDetails.getUsername());
        if(!flag) {
            return ResponseFactory.conflict();
        }

        return ResponseFactory.ok();
    }

}
