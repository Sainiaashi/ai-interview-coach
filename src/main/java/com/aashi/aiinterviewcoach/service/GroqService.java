package com.aashi.aiinterviewcoach.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aashi.aiinterviewcoach.dto.GroqRequest;
import com.aashi.aiinterviewcoach.dto.GroqResponse;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public GroqService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateInterviewQuestions(String resumeText) {

        String prompt = """
                You are an experienced Java Backend interviewer.

                Based on the following resume:

                %s

                Generate:

                1. Five Java interview questions.
                2. Three Spring Boot interview questions.
                3. Two project-specific interview questions.

                Return only the questions.
                """.formatted(resumeText);

        GroqRequest.Message message =
                new GroqRequest.Message("user", prompt);

        GroqRequest request = new GroqRequest(
                "llama-3.3-70b-versatile",
                List.of(message)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<GroqRequest> entity =
                new HttpEntity<>(request, headers);
         
                GroqResponse response = restTemplate.postForObject(
        apiUrl,
        entity,
        GroqResponse.class
);

if (response == null
        || response.getChoices() == null
        || response.getChoices().isEmpty()) {

    return "No interview questions generated.";
}

return response.getChoices()
               .get(0)
               .getMessage()
               .getContent();
    }
}