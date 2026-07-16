package com.aashi.aiinterviewcoach.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="resumes")
public class Resume{
    @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY) 
   private Long id;

   private String filename;

   private String fileUrl;
   
   @CreationTimestamp
   private LocalDateTime uploadedAt;

   @JsonManagedReference
   @ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private User user;
}