package com.mungta.noticeservice.domain.notice.dto.request;

import com.mungta.noticeservice.domain.notice.model.vo.NoticeContents;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeRegisterRequest {

    @ApiModelProperty(value = "관리자 id", required = true, example = "1")
    private Long adminId;

    @ApiModelProperty(value = "공지사항 컨텐츠", required = true)
    private NoticeContents notice;
}
