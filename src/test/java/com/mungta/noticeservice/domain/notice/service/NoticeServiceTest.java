package com.mungta.noticeservice.domain.notice.service;

import com.mungta.noticeservice.common.ApiException;
import com.mungta.noticeservice.common.ApiStatus;
import com.mungta.noticeservice.domain.notice.dto.response.NoticeListResponse;
import com.mungta.noticeservice.domain.notice.model.Notice;
import com.mungta.noticeservice.domain.notice.model.enums.DisplayStatus;
import com.mungta.noticeservice.domain.notice.model.vo.NoticeContents;
import com.mungta.noticeservice.domain.notice.repository.NoticeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static com.mungta.noticeservice.domain.notice.sample.NoticeTestSample.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(value = MockitoExtension.class)
public class NoticeServiceTest {

    @InjectMocks
    @Spy
    private NoticeService noticeService;

    @Mock
    private NoticeRepository noticeRepository;

    private Notice notice;

    @BeforeEach
    void setUp(){

        notice = Notice.of(
                NOTICE_ADMIN_ID,
                NoticeContents.builder()
                        .title(NOTICE_TITLE)
                        .body(NOTICE_BODY)
                        .build()
        );
        notice.setId(NOTICE_ID);
        notice.setDisplayStatus(NOTICE_DISPLAY_STATUS);

    }
    @DisplayName("공지사항 등록")
    @Test
    void registerNotice() {
        noticeService.registerNotice(NOTICE_ADMIN_ID, NOTICE_REGISTER_REQUEST);

        verify(noticeRepository).save(any());
    }

    @DisplayName("공지사항 ID 조회")
    @Test
    void findShowNoticeById() {
        given(noticeRepository.findByIdAndDisplayStatus(NOTICE_ID, DisplayStatus.SHOW))
                .willReturn(Optional.ofNullable(notice));

        Notice notice1 = noticeService.findShowNoticeById(NOTICE_ID);

        assertAll(
                ()->assertThat(notice1.getId()).isEqualTo(NOTICE_ID),
                ()->assertThat(notice1.getAdminId()).isEqualTo(NOTICE_ADMIN_ID),
                ()->assertThat(notice1.getNotice().getTitle()).isEqualTo(NOTICE_TITLE),
                ()->assertThat(notice1.getNotice().getBody()).isEqualTo(NOTICE_BODY),
                ()->assertThat(notice1.getDisplayStatus()).isEqualTo(NOTICE_DISPLAY_STATUS)
        );

    }

    @DisplayName("공지사항 없는 ID 조회")
    @Test
    void findShowNoticeById_not_exist_notice() {
        given(noticeRepository.findByIdAndDisplayStatus(2L, DisplayStatus.SHOW))
                .willThrow(new ApiException(ApiStatus.NOT_EXIST_NOTICE));

        assertThatThrownBy(()->noticeService.findShowNoticeById(2L))
                .isInstanceOf(ApiException.class)
                .hasMessage("공지사항이 존재하지 않습니다.");
    }

    @DisplayName("공지사항 리스트 조회")
    @Test
    void findNoticeListResponse() {
        given(noticeRepository.findAllByDisplayStatusOrderByCreatedDateDesc(DisplayStatus.SHOW))
                .willReturn(List.of(notice));

        List<NoticeListResponse> noticeListResponses = noticeService.findNoticeListResponse();

        assertAll(
                ()->assertThat(noticeListResponses.get(0).getId()).isEqualTo(NOTICE_ID),
                ()->assertThat(noticeListResponses.get(0).getTitle()).isEqualTo(NOTICE_TITLE)
        );
    }

    @DisplayName("공지사항 삭제")
    @Test
    void deleteNotice() {
        given(noticeRepository.findByIdAndDisplayStatus(NOTICE_ID, DisplayStatus.SHOW))
                .willReturn(Optional.ofNullable(notice));
        noticeService.deleteNotice(NOTICE_ID);

        verify(noticeRepository).save(any());
    }

}
