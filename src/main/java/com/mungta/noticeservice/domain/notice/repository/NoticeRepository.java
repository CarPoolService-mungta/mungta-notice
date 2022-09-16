package com.mungta.noticeservice.domain.notice.repository;

import com.mungta.noticeservice.domain.notice.model.Notice;
import com.mungta.noticeservice.domain.notice.model.enums.DisplayStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByIdAndDisplayStatus(Long id, DisplayStatus displayStatus);
    List<Notice> findAllByDisplayStatusOrderByCreatedDateDesc(DisplayStatus displayStatus);
}
