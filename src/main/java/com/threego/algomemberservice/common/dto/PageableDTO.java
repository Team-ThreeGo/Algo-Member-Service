package com.threego.algomemberservice.common.dto;

import lombok.Getter;
import lombok.Setter;

/* 설명. 페이지네이션을 위한 공통 추상 클래스
 *       목록 조회가 필요한 SearchDTO들은 이 클래스를 상속받아 사용
 * */
@Getter
@Setter
public abstract class PageableDTO {

    /* 설명. 페이지 번호 (0부터 시작) */
    private Integer page;

    /* 설명. 페이지 크기 (한 페이지당 조회할 데이터 개수) */
    private Integer size;

    /* 설명. 정렬 기준 필드 */
    private String sortBy;

    /* 설명. 정렬 방향 (ASC, DESC) */
    private String sortDirection;

    /* 설명. 페이지 번호 반환 (기본값: 0) */
    public int getPage() {
        return page != null ? page : 0;
    }


    /* 설명. 페이지 크기 반환 (기본값: 10) */
    public int getSize() {
        return size != null ? size : 10;
    }

    /* 설명. 정렬 방향 반환 (기본값: DESC) */
    public String getSortDirection() {
        return sortDirection != null ? sortDirection : "DESC";
    }

    /* 설명. OFFSET 계산 (MyBatis에서 사용) */
    public int getOffset() {
        return getPage() * getSize();
    }
}