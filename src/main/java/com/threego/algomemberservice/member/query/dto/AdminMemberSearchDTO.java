package com.threego.algomemberservice.member.query.dto;

import com.threego.algomemberservice.common.dto.PageableDTO;
import com.threego.algomemberservice.member.command.domain.aggregate.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class AdminMemberSearchDTO extends PageableDTO{
    private String keyword;
    private String status;

    // Builder 패턴 지원 (부모 클래스 필드 포함)
    @Builder
    public AdminMemberSearchDTO(Integer page, Integer size, String sortBy, String sortDirection,
                                String status, String keyword) {
        super.setPage(page);
        super.setSize(size);
        super.setSortBy(sortBy);
        super.setSortDirection(sortDirection);
        this.status = status;
        this.keyword = keyword;
    }

    // 정렬 기준 기본값 오버라이드
    @Override
    public String getSortBy() {
        String sortBy = super.getSortBy();
        return sortBy != null ? sortBy : "id";
    }
}
