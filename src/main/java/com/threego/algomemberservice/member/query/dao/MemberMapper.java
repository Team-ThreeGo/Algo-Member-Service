package com.threego.algomemberservice.member.query.dao;

import com.threego.algomemberservice.member.query.dto.AdminMemberDetailResponseDTO;
import com.threego.algomemberservice.member.query.dto.MemberDetailResponseDTO;
import com.threego.algomemberservice.member.query.dto.AdminMemberSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    MemberDetailResponseDTO selectMemberById(
            @Param("id") int id
    );

    AdminMemberDetailResponseDTO selectMemberDetailsById(
            @Param("id") int id
    );

    List<AdminMemberDetailResponseDTO> selectAllMemberDetails(AdminMemberSearchDTO searchDTO);
    int countAllMembers(AdminMemberSearchDTO searchDTO);

    String findRankNameById(@Param("memberId") final int memberId);
}