package com.threego.algomemberservice.member.query.service;

import com.threego.algomemberservice.member.query.dao.MemberMapper;
import com.threego.algomemberservice.member.query.dto.MemberDetailResponseDTO;
import com.threego.algomemberservice.member.query.dto.MemberInfoResponseDTO;
import com.threego.algomemberservice.member.query.dto.PostSummaryResponseDto;
import com.threego.algomemberservice.member.query.infrastructure.CareerInfoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberMapper memberMapper;
    private final CareerInfoClient careerInfoClient;

    @Autowired
    public MemberQueryServiceImpl(MemberMapper memberMapper, CareerInfoClient careerInfoClient) {
        this.memberMapper = memberMapper;
        this.careerInfoClient = careerInfoClient;
    }


    @Override
    public MemberDetailResponseDTO findMemberById(int id) {
        MemberDetailResponseDTO dto = memberMapper.selectMemberById(id);
        return dto;
    }

    @Override
    public List<PostSummaryResponseDto> getMyPosts(int memberId) {
        return careerInfoClient.getPostsByMember(memberId);
    }

    @Override
    public MemberInfoResponseDTO findNickNameAndRankNameById(final int memberId) {
        return memberMapper.findNickNameAndRankNameById(memberId);
    }
}
