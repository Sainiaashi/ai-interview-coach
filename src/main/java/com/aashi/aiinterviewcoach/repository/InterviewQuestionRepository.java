package com.aashi.aiinterviewcoach.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aashi.aiinterviewcoach.entity.InterviewQuestion;

public interface InterviewQuestionRepository
        extends JpaRepository<InterviewQuestion, Long> {

    List<InterviewQuestion> findByUserId(Long userId);
}