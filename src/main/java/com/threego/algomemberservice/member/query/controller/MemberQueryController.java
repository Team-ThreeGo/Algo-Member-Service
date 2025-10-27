package com.threego.algomemberservice.member.query.controller;

import com.threego.algomemberservice.member.query.dto.MemberDetailResponseDTO;
import com.threego.algomemberservice.member.query.dto.PostSummaryResponseDto;
import com.threego.algomemberservice.member.query.service.MemberQueryService;
import com.threego.algomemberservice.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "Member - Member Query",
        description = "회원용 회원 API (Query)"
)
@RestController
@RequestMapping("/member")
public class MemberQueryController {
    private final MemberQueryService memberQueryService;

    public MemberQueryController(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    @Operation(
            summary = "회원 정보 조회",
            description = "id를 통해 회원을 검색할 수 있습니다."
    )
    @GetMapping("/{id}/info")
    public ResponseEntity<MemberDetailResponseDTO> findMemberById(
            @PathVariable int id
    ) {
        return ResponseEntity.ok(memberQueryService.findMemberById(id));
    }

    @Operation(
            summary = "회원 등급 조회",
            description = "id를 통해 회원 등급을 조회할 수 있습니다."
    )
    @GetMapping("/rank")
    public ResponseEntity<String> findRankNameById(@AuthenticationPrincipal final CustomUserDetails userDetails) {
        return ResponseEntity.ok(memberQueryService.findRankNameById(userDetails.getMemberId()));
    }

    @GetMapping("/me/career-posts")
    public ResponseEntity<List<PostSummaryResponseDto>> getMyCareerPosts(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        int memberId = user.getMemberId();
        return ResponseEntity.ok(memberQueryService.getMyPosts(memberId));
    }

}
