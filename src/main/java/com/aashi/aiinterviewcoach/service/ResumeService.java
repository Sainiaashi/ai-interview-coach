package com.aashi.aiinterviewcoach.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aashi.aiinterviewcoach.dto.ResumeUploadResponse;
import com.aashi.aiinterviewcoach.entity.InterviewQuestion;
import com.aashi.aiinterviewcoach.entity.Resume;
import com.aashi.aiinterviewcoach.entity.User;
import com.aashi.aiinterviewcoach.repository.InterviewQuestionRepository;
import com.aashi.aiinterviewcoach.repository.ResumeRepository;
import com.aashi.aiinterviewcoach.repository.UserRepository;
import com.aashi.aiinterviewcoach.service.PdfService;

@Service
public class ResumeService {

    private static final String UPLOAD_DIR = "E:/spring boot/aiinterviewcoach/ai-interview-coach/uploads/resumes";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final PdfService pdfService;
    private final GroqService groqService;
    private final InterviewQuestionRepository questionRepository;

    public ResumeService(
        ResumeRepository resumeRepository,
        UserRepository userRepository,
        PdfService pdfService,
        GroqService groqService,
        InterviewQuestionRepository questionRepository) {

    this.resumeRepository = resumeRepository;
    this.userRepository = userRepository;
    this.pdfService = pdfService;
    this.groqService = groqService;
    this.questionRepository = questionRepository;
}

    public ResumeUploadResponse uploadResume(MultipartFile file, Long userId) {

        // Validate empty file
        if (file == null || file.isEmpty()) {
            return new ResumeUploadResponse("File must not be empty.", null);
        }

        String originalFileName = file.getOriginalFilename();

        // Validate PDF
        if (originalFileName == null
                || !"application/pdf".equals(file.getContentType())
                || !originalFileName.toLowerCase().endsWith(".pdf")) {

            return new ResumeUploadResponse("Only PDF files are allowed.", null);
        }

        // Validate size
        if (file.getSize() > MAX_FILE_SIZE) {
            return new ResumeUploadResponse("File size must not exceed 5 MB.", null);
        }

        // Find user
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return new ResumeUploadResponse("User not found.", null);
        }

        User user = optionalUser.get();

        Path filePath = null;

        try {

            // Delete old resume
            Optional<Resume> optionalResume = resumeRepository.findByUserId(userId);

            if (optionalResume.isPresent()) {

                Resume oldResume = optionalResume.get();

                Path oldPath = Paths.get(oldResume.getFileUrl());

                Files.deleteIfExists(oldPath);

                resumeRepository.delete(oldResume);
            }

            // Create upload directory
            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String extension =
                    originalFileName.substring(originalFileName.lastIndexOf("."));

            String fileName =
                    userId + "_" + System.currentTimeMillis() + extension;

            filePath = uploadPath.resolve(fileName);

            // Save PDF
            file.transferTo(filePath.toFile());
            String resumeText = pdfService.extractText(filePath);

System.out.println("========== RESUME TEXT ==========");
System.out.println(resumeText);
System.out.println("=================================");

String questions = groqService.generateInterviewQuestions(resumeText);

String[] lines = questions.split("\n");

for (String line : lines) {

    line = line.trim();

    if (line.isEmpty()) {
        continue;
    }

    InterviewQuestion question = InterviewQuestion.builder()
            .question(line)
            .category("GENERAL")
            .createdAt(LocalDateTime.now())
            .user(user)
            .build();

    questionRepository.save(question);
}

            // Save database record
            Resume resume = Resume.builder()
                    .filename(fileName)
                    .fileUrl(filePath.toString())
                    .uploadedAt(LocalDateTime.now())
                    .user(user)
                    .build();

            resumeRepository.save(resume);

            return new ResumeUploadResponse(
                    "Resume uploaded successfully.",
                    fileName);

        } catch (IOException e) {

            if (filePath != null) {
                try {
                    Files.deleteIfExists(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            return new ResumeUploadResponse(
                    "Failed to upload resume."+":"+e.getMessage(),
                    null);
        }
    }
   
}