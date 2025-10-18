package com.threego.algomemberservice.member.query.controller;

import com.threego.algomemberservice.common.dto.PagedResponseDTO;
import com.threego.algomemberservice.member.query.dto.AdminMemberDetailResponseDTO;
import com.threego.algomemberservice.member.query.dto.AdminMemberSearchDTO;
import com.threego.algomemberservice.member.query.service.AdminMemberQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Member - Admin Query",
        description = "관리자용 회원 관리 API (Query)"
)
@RestController
@RequestMapping("/admin/member")
public class AdminMemberQueryController {
    AdminMemberQueryService adminMemberQueryService;

    @Autowired
    public AdminMemberQueryController(AdminMemberQueryService adminMemberQueryService) {
        this.adminMemberQueryService = adminMemberQueryService;
    }

    @Operation(
            summary = "관리자용 회원 정보 조회",
            description = "관리자는 특정 회원의 정보를 조회할 수 있다."
    )
    @GetMapping("/{id}/info")
    public ResponseEntity<AdminMemberDetailResponseDTO> findMemberDetailsById(
            @PathVariable int id
    ){
        return ResponseEntity.ok(adminMemberQueryService.findMemberDetailsById(id));
    }

    @Operation(
            summary = "관리자용 모든 회원 목록 조회",
            description = "관리자는 모든 회원의 목록을 조회할 수 있다."
    )
    @GetMapping("/info")
    public ResponseEntity<PagedResponseDTO<AdminMemberDetailResponseDTO>> findMemberList(
            @ModelAttribute AdminMemberSearchDTO searchDTO
    ) {
        return ResponseEntity.ok(adminMemberQueryService.findMemberList(searchDTO));
    }
}
