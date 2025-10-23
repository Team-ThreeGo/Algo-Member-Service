package com.threego.algomemberservice.member.query.service;


import com.threego.algomemberservice.common.dto.PagedResponseDTO;
import com.threego.algomemberservice.member.query.dto.AdminMemberDetailResponseDTO;
import com.threego.algomemberservice.member.query.dto.AdminMemberSearchDTO;

import java.util.List;

public interface AdminMemberQueryService {
    AdminMemberDetailResponseDTO findMemberDetailsById(int id);

    PagedResponseDTO<AdminMemberDetailResponseDTO> findMemberList(AdminMemberSearchDTO searchDTO);
}
