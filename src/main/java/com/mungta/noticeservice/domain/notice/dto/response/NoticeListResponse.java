package com.mungta.noticeservice.domain.notice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NoticeListResponse {
    private Long id;
    private String title;
    private LocalDate createDate;

    public static NoticeListResponse of(Long id, String title, LocalDate createDate){
        return NoticeListResponse.builder()
                .id(id)
                .title(title)
                .createDate(createDate)
                .build();
    }
}
