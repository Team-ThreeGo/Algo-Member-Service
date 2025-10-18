package com.threego.algomemberservice.member.command.application.service;

import com.threego.algomemberservice.common.error.exception.EntityNotFoundException;
import com.threego.algomemberservice.member.aop.IncreasePoint;
import com.threego.algomemberservice.member.aop.MemberPointAspect;
import com.threego.algomemberservice.member.command.domain.aggregate.Member;
import com.threego.algomemberservice.member.command.domain.aggregate.MemberRank;
import com.threego.algomemberservice.member.command.domain.aggregate.enums.RankName;
import com.threego.algomemberservice.member.command.domain.repository.MemberCommandRepository;
import com.threego.algomemberservice.member.command.domain.repository.MemberRankRepository;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberCommandServiceImplTest {
    @Mock
    MemberCommandRepository memberRepository;

    @Mock
    MemberRankRepository memberRankRepository;

    @InjectMocks
    MemberCommandServiceImpl memberService;

    MemberPointAspect memberPointAspect;

    Member member;
    MemberRank rankKoalMot;
    MemberRank rankKoNewbie;

    @BeforeEach
    void setup() {
        memberPointAspect = new MemberPointAspect(memberRepository, memberRankRepository);
    }

    @DisplayName("닉네임 변경 실패 테스트")
    @Test
    void MemberNickNameUpdateFailTest() {
        // given
        when(memberRepository.findById(any())).thenReturn(Optional.empty());

        // when, then
        assertThrows(EntityNotFoundException.class,
                () -> memberService.updateMemberInfo(100, "닉네임"));
    }

    @DisplayName("포인트 상승 및 등급업 AOP 직접 호출 테스트")
    @Test
    void 포인트상승_직접호출() throws Throwable {
        // given
        MemberRank rank1 = new MemberRank();
        rank1.setName(RankName.코알못);
        rank1.setMinPoint(0);
        rank1.setImageUrl("img1.png");

        MemberRank rank2 = new MemberRank();
        rank2.setName(RankName.코뉴비);
        rank2.setMinPoint(20);
        rank2.setImageUrl("img2.png");

        when(memberRankRepository.findAll()).thenReturn(List.of(rank1, rank2));

        Member member = new Member("email", "pw", "nick", rank1, "2025-10-18");
        member.setId(1);
        member.setPoint(19);

        when(memberRepository.findById(1)).thenReturn(Optional.of(member));

        // Mock JoinPoint with memberId argument
        JoinPoint joinPoint = mock(JoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[]{1});

        // Mock annotation
        IncreasePoint increasePoint = mockIncreasePointAnnotation(1);

        // when — 수동으로 AOP 호출
        memberPointAspect.handlePointAfterReturning(joinPoint, increasePoint, "OK");

        // then
        assertEquals(20, member.getPoint());
        assertEquals(RankName.코뉴비, member.getMemberRank().getName());
        verify(memberRepository, times(1)).save(member);
    }

    private IncreasePoint mockIncreasePointAnnotation(int amount) {
        return new IncreasePoint() {
            @Override
            public int amount() {
                return amount;
            }

            @Override
            public boolean useArgumentMemberId() {
                return true;
            }

            @Override
            public boolean onStatusChangeApproved() {
                return false;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return IncreasePoint.class;
            }
        };
    }
}