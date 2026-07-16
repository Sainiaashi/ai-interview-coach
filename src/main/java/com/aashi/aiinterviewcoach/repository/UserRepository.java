package com.aashi.aiinterviewcoach.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.aashi.aiinterviewcoach.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}