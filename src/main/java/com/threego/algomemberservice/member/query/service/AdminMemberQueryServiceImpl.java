package com.threego.algomemberservice.member.query.service;

import com.threego.algomemberservice.common.dto.PagedResponseDTO;
import com.threego.algomemberservice.member.query.dao.MemberMapper;
import com.threego.algomemberservice.member.query.dto.AdminMemberDetailResponseDTO;
import com.threego.algomemberservice.member.query.dto.AdminMemberSearchDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminMemberQueryServiceImpl implements AdminMemberQueryService {
    private final MemberMapper memberMapper;

    public AdminMemberQueryServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public AdminMemberDetailResponseDTO findMemberDetailsById(int id) {
        AdminMemberDetailResponseDTO dto = memberMapper.selectMemberDetailsById(id);
        return dto;
    }

    @Override
    public PagedResponseDTO<AdminMemberDetailResponseDTO> findMemberList(AdminMemberSearchDTO searchDTO) {
        List<AdminMemberDetailResponseDTO> members = memberMapper.selectAllMemberDetails(searchDTO);
        int total = memberMapper.countAllMembers(searchDTO);
        return new PagedResponseDTO<>(members, searchDTO.getPage(), searchDTO.getSize(), total);
    }
}
