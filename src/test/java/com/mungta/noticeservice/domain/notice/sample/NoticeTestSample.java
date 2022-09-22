package com.mungta.noticeservice.domain.notice.sample;

import com.mungta.noticeservice.domain.notice.dto.request.NoticeRegisterRequest;
import com.mungta.noticeservice.domain.notice.model.enums.DisplayStatus;
import com.mungta.noticeservice.domain.notice.model.vo.NoticeContents;

public class NoticeTestSample {

    public static final Long NOTICE_ID=1L;
    public static final String NOTICE_ADMIN_ID = "oneshap";
    public static final String NOTICE_TITLE = "공지사항 제목";
    public static final String NOTICE_BODY = "공지사항 내용";
    public static final DisplayStatus NOTICE_DISPLAY_STATUS = DisplayStatus.SHOW;

    public static final NoticeRegisterRequest NOTICE_REGISTER_REQUEST;
    static{
        NOTICE_REGISTER_REQUEST = NoticeRegisterRequest.builder()
                .notice(NoticeContents.builder()
                        .title(NOTICE_TITLE)
                        .body(NOTICE_BODY)
                        .build())
                .build();
    }

}
