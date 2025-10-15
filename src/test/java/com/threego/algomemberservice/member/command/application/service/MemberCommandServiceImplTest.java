package com.threego.algomemberservice.member.command.application.service;

import com.threego.algomemberservice.common.error.exception.EntityNotFoundException;
import com.threego.algomemberservice.member.command.domain.repository.MemberCommandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberCommandServiceImplTest {
    @Mock
    MemberCommandRepository memberCommandRepository;

    @InjectMocks
    MemberCommandServiceImpl memberCommandService;

    @DisplayName("닉네임 변경 실패 테스트")
    @Test
    void MemberNickNameUpdateFailTest() {
        // given
        when(memberCommandRepository.findById(any())).thenReturn(Optional.empty());

        // when, then
        assertThrows(EntityNotFoundException.class,
                () -> memberCommandService.updateMemberInfo(100, "닉네임"));
    }
}