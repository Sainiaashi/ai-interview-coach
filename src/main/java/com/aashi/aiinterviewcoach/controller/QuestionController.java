package com.aashi.aiinterviewcoach.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.aashi.aiinterviewcoach.dto.InterviewQuestionResponse;
import com.aashi.aiinterviewcoach.entity.User;
import com.aashi.aiinterviewcoach.repository.UserRepository;
import com.aashi.aiinterviewcoach.service.QuestionService;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final UserRepository userRepository;

    public QuestionController(QuestionService questionService,
                              UserRepository userRepository) {
        this.questionService = questionService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<InterviewQuestionResponse> getQuestions(
            Authentication authentication) {

        String email = authentication.getName();

        Optional<User> user = userRepository.findByEmail(email);

        return questionService.getQuestions(user.get().getId());
    }
}
