package com.aashi.aiinterviewcoach.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.aashi.aiinterviewcoach.dto.ResumeUploadResponse;
import com.aashi.aiinterviewcoach.entity.User;
import com.aashi.aiinterviewcoach.repository.UserRepository;
import com.aashi.aiinterviewcoach.service.ResumeService;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/upload")
    public ResumeUploadResponse upload(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {

        String email = authentication.getName();

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            return new ResumeUploadResponse("User not found", null);
        }

        Long userId = user.get().getId();

        return resumeService.uploadResume(file, userId);
    }
}