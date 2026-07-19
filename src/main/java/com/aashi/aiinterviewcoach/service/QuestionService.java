package com.aashi.aiinterviewcoach.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aashi.aiinterviewcoach.dto.InterviewQuestionResponse;
import com.aashi.aiinterviewcoach.repository.InterviewQuestionRepository;

@Service
public class QuestionService {

    private final InterviewQuestionRepository repository;

    public QuestionService(InterviewQuestionRepository repository) {
        this.repository = repository;
    }

    public List<InterviewQuestionResponse> getQuestions(Long userId) {

        return repository.findByUserId(userId)
                .stream()
                .map(q -> InterviewQuestionResponse.builder()
                        .question(q.getQuestion())
                        .category(q.getCategory())
                        .build())
                .toList();
    }
}