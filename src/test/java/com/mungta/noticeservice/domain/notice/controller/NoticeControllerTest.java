package com.mungta.noticeservice.domain.notice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mungta.noticeservice.domain.notice.dto.request.NoticeRegisterRequest;
import com.mungta.noticeservice.domain.notice.model.Notice;
import com.mungta.noticeservice.domain.notice.model.enums.DisplayStatus;
import com.mungta.noticeservice.domain.notice.model.vo.NoticeContents;
import com.mungta.noticeservice.domain.notice.service.NoticeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static com.mungta.noticeservice.domain.notice.sample.NoticeTestSample.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(NoticeController.class)
public class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private NoticeService noticeService;

    private Notice notice;

    @BeforeEach
    void setUp() {
        //한글 깨짐...
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();

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

    @DisplayName("[관리자] 공지사항 등록 API")
    @Test
    void registerNotice() throws Exception{
        doNothing().when(noticeService).registerNotice(NOTICE_ADMIN_ID,NOTICE_REGISTER_REQUEST);
        ResultActions resultActions = mockMvc.perform(
                post("/api/notice/admin/notice")
                        .header("userId", NOTICE_ADMIN_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(NOTICE_REGISTER_REQUEST))
        );

        resultActions.andExpect(status().isNoContent());
    }

    @DisplayName("[회원] 특정 공지사항 조회 API")
    @Test
    void findShowNoticeResponse() throws Exception{
        doReturn(notice)
                .when(noticeService).findShowNoticeById(NOTICE_ID);

        ResultActions resultActions = mockMvc.perform(
                get("/api/notice/notice-show")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("id", NOTICE_ID.toString())
        );

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath(("$.id")).value(NOTICE_ID))
                .andExpect(jsonPath(("$.adminId")).value(NOTICE_ADMIN_ID))
                .andExpect(jsonPath(("$.notice.title")).value(NOTICE_TITLE))
                .andExpect(jsonPath(("$.notice.body")).value(NOTICE_BODY))
                .andExpect(jsonPath(("$.displayStatus")).value(NOTICE_DISPLAY_STATUS.toString()));

    }

    @DisplayName("[회원] 공지사항 리스트 조회 API")
    @Test
    void findNoticeListResponse() throws Exception{
        doReturn(List.of(notice.convertListView()))
                .when(noticeService).findNoticeListResponse();

        ResultActions resultActions = mockMvc.perform(
                get("/api/notice/notice-list")
                        .accept(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(NOTICE_ID))
                .andExpect(jsonPath("$[0].title").value(NOTICE_TITLE));
    }

    @DisplayName("[관리자] 공지사항 삭제 API")
    @Test
    void deleteNotice() throws Exception{
        doNothing()
                .when(noticeService).deleteNotice(NOTICE_ID);

        ResultActions resultActions = mockMvc.perform(
                delete("/api/notice/admin/notice")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("id",NOTICE_ID.toString())
        );

        resultActions.andExpect(status().isNoContent());
    }

}
