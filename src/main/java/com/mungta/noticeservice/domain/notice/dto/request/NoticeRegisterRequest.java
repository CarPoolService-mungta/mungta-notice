package com.mungta.noticeservice.domain.notice.dto.request;

import com.mungta.noticeservice.domain.notice.model.vo.NoticeContents;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeRegisterRequest {

    @ApiModelProperty(value = "공지사항 컨텐츠", required = true)
    private NoticeContents notice;
}
