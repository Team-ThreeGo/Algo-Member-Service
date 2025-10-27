package com.threego.algomemberservice.member.query.service;

import com.threego.algomemberservice.member.query.dto.MemberDetailResponseDTO;
import com.threego.algomemberservice.member.query.dto.MemberInfoResponseDTO;
import com.threego.algomemberservice.member.query.dto.PostSummaryResponseDto;

import java.util.List;

public interface MemberQueryService {
    MemberDetailResponseDTO findMemberById (int id);

    List<PostSummaryResponseDto> getMyPosts(int memberId);

    MemberInfoResponseDTO findNickNameAndRankNameById(final int memberId);
}
