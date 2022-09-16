package com.mungta.noticeservice.domain.notice.service;

import com.mungta.noticeservice.common.ApiException;
import com.mungta.noticeservice.common.ApiStatus;
import com.mungta.noticeservice.domain.notice.dto.request.NoticeRegisterRequest;
import com.mungta.noticeservice.domain.notice.dto.response.NoticeListResponse;
import com.mungta.noticeservice.domain.notice.model.Notice;
import com.mungta.noticeservice.domain.notice.model.enums.DisplayStatus;
import com.mungta.noticeservice.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public void registerNotice(NoticeRegisterRequest noticeRegisterRequest){
        noticeRepository.save(Notice.of(noticeRegisterRequest.getAdminId(),noticeRegisterRequest.getNotice()));
    }

    public Notice findShowNoticeById(Long id){
        return noticeRepository.findByIdAndDisplayStatus(id, DisplayStatus.SHOW)
                .orElseThrow(()-> new ApiException(ApiStatus.NOT_EXIST_NOTICE));
    }

    public List<NoticeListResponse> findNoticeListResponse(){
        return noticeRepository.findAllByDisplayStatusOrderByCreatedDateDesc(DisplayStatus.SHOW)
                .stream().map(Notice::convertListView)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteNotice(Long id){
        Notice notice = findShowNoticeById(id);
        notice.setDisplayStatus(DisplayStatus.DELETE);
        noticeRepository.save(notice);
    }
}
