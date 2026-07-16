package com.aashi.aiinterviewcoach.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.aashi.aiinterviewcoach.entity.Resume;
import java.util.*;

public interface ResumeRepository extends JpaRepository<Resume,Long>
{
    Optional<Resume> findByUserId(Long userId);
}