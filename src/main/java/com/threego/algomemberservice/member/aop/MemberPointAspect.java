package com.threego.algomemberservice.member.aop;

import com.threego.algomemberservice.member.command.domain.aggregate.Member;
import com.threego.algomemberservice.member.command.domain.aggregate.MemberRank;
import com.threego.algomemberservice.member.command.domain.repository.MemberCommandRepository;
import com.threego.algomemberservice.member.command.domain.repository.MemberRankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MemberPointAspect {

    private final MemberCommandRepository memberRepository;
    private final MemberRankRepository memberRankRepository;

    @AfterReturning(value = "@annotation(increasePoint)", returning = "result")
    @Transactional
    public void handlePointAfterReturning(JoinPoint joinPoint, IncreasePoint increasePoint, Object result) {
        if (increasePoint.onStatusChangeApproved()) return; // 승인용 아님

        Object[] args = joinPoint.getArgs();
        Integer memberId = null;
        if (increasePoint.useArgumentMemberId()) {
            for (Object arg : args) {
                if (arg instanceof Integer id && id > 0) { memberId = id; break; }
            }
        } else if (result instanceof Integer id && id > 0) {
            memberId = id;
        }

        if (memberId != null) {
            final int finalPostId = memberId;
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("회원 없음: " + finalPostId));
            increaseMemberPoint(member, increasePoint.amount());
        }
    }

    // 공통
    private void increaseMemberPoint(Member member, int amount) {
        MemberRank oldRank = member.getMemberRank();
        member.increasePoint(amount);
        member.updateRank(memberRankRepository.findAll());
        MemberRank newRank = member.getMemberRank();
        memberRepository.save(member);

        if (!oldRank.equals(newRank)) {
            log.info("🎉 [등급업] 회원 {}({}) '{}' → '{}' (현재 포인트: {})",
                    member.getId(), member.getNickname(),
                    oldRank.getName().name(), newRank.getName().name(), member.getPoint());
        } else {
            log.info("[포인트] 회원 {}({}) +{}점 (현재 포인트: {})",
                    member.getId(), member.getNickname(),
                    amount, member.getPoint());
        }
    }
}