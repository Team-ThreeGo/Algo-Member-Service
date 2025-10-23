package com.threego.algomemberservice.member.query.service;

import com.threego.algomemberservice.common.dto.PagedResponseDTO;
import com.threego.algomemberservice.member.query.dao.MemberMapper;
import com.threego.algomemberservice.member.query.dto.AdminMemberDetailResponseDTO;
import com.threego.algomemberservice.member.query.dto.AdminMemberSearchDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminMemberQueryServiceImplTest {

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private AdminMemberQueryServiceImpl adminMemberQueryService;

    private AdminMemberDetailResponseDTO member1;
    private AdminMemberDetailResponseDTO member2;

    @BeforeEach
    void setUp() {
        member1 = AdminMemberDetailResponseDTO.builder()
                .id(1)
                .email("user1@test.com")
                .nickname("User1")
                .rank("코뉴비")
                .role("USER")
                .point(100)
                .status("ACTIVE")
                .reportedCount(0)
                .createdAt("2025-10-18T12:00:00")
                .build();

        member2 = AdminMemberDetailResponseDTO.builder()
                .id(2)
                .email("user2@test.com")
                .nickname("User2")
                .rank("코좀알")
                .role("USER")
                .point(200)
                .status("ACTIVE")
                .reportedCount(1)
                .createdAt("2025-10-17T12:00:00")
                .build();
    }

    @DisplayName("회원 단일 조회 테스트")
    @Test
    void findMemberDetailsByIdTest() {
        // given
        when(memberMapper.selectMemberDetailsById(1)).thenReturn(member1);

        // when
        AdminMemberDetailResponseDTO result = adminMemberQueryService.findMemberDetailsById(1);

        // then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("User1", result.getNickname());
        verify(memberMapper, times(1)).selectMemberDetailsById(1);
    }

    @DisplayName("회원 목록 조회 + 페이징 + 검색 + 정렬 테스트")
    @Test
    void findMemberListWithSearchAndSortTest() {
        // given
        AdminMemberSearchDTO searchDTO = new AdminMemberSearchDTO();
        searchDTO.setPage(1); // 2번째 페이지
        searchDTO.setSize(5); // 페이지당 5명
        searchDTO.setKeyword("User"); // 검색 키워드
        searchDTO.setSortBy("POINT"); // 정렬 기준
        searchDTO.setSortDirection("DESC"); // 내림차순

        List<AdminMemberDetailResponseDTO> mockMembers = List.of(member2); // 조건에 맞는 mock 데이터

        when(memberMapper.selectAllMemberDetails(searchDTO)).thenReturn(mockMembers);
        when(memberMapper.countAllMembers(searchDTO)).thenReturn(1);

        // when
        PagedResponseDTO<AdminMemberDetailResponseDTO> result = adminMemberQueryService.findMemberList(searchDTO);

        // then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getPage());
        assertEquals(5, result.getSize());

        // 검색, 정렬 조건이 DTO에 정확히 반영되었는지 확인
        assertEquals("User", searchDTO.getKeyword());
        assertEquals("POINT", searchDTO.getSortBy());
        assertEquals("DESC", searchDTO.getSortDirection());

        verify(memberMapper, times(1)).selectAllMemberDetails(searchDTO);
        verify(memberMapper, times(1)).countAllMembers(searchDTO);
    }

}