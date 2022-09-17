package com.mungta.noticeservice.domain.notice.controller;

import com.mungta.noticeservice.domain.notice.dto.request.NoticeRegisterRequest;
import com.mungta.noticeservice.domain.notice.dto.response.NoticeListResponse;
import com.mungta.noticeservice.domain.notice.model.Notice;
import com.mungta.noticeservice.domain.notice.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = { "공지사항 Controller" })
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;


    @ApiOperation(value = "공지사항 등록", notes = "공지사항 등록 api")
    @PostMapping("/admin/notice")
    public ResponseEntity registerNotice(@RequestHeader("userId") String adminId,
                                         @RequestBody NoticeRegisterRequest noticeRegisterRequest){
        noticeService.registerNotice(adminId, noticeRegisterRequest);
        return ResponseEntity.noContent().build();
    }
    @ApiOperation(value = "특정 공지사항 조회", notes = "특정 공지사항 조회 api")
    @GetMapping("/notice-show")
    public ResponseEntity<Notice> findShowNoticeResponse(@ApiParam(value = "공지사항 id", required = true)  @RequestParam Long id){
        return ResponseEntity.ok(noticeService.findShowNoticeById(id));
    }
    @ApiOperation(value = "공지사항 리스트 조회", notes = "공지사항 리스트 조회 api")
    @GetMapping("/notice-list")
    public ResponseEntity<List<NoticeListResponse>> findNoticeListResponse(){
        return ResponseEntity.ok(noticeService.findNoticeListResponse());
    }
    @ApiOperation(value = "공지사항 삭제", notes = "공지사항 삭제 api")
    @DeleteMapping("/admin/notice")
    public ResponseEntity deleteNotice(@ApiParam(value = "공지사항 id", required = true)  @RequestParam Long id){
        noticeService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }
}
