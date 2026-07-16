package com.aashi.aiinterviewcoach.entity;
import jakarta.persistence.*;
import java.util.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User{

   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY) 
   private Long id;

   private String fullname;

   @Column(name="email",nullable=false,unique=true)
   private String email;
   private String password;

   @Enumerated(EnumType.STRING) 
   private Role role;

   @CreationTimestamp
   private LocalDateTime createdAt;
   @UpdateTimestamp
   private LocalDateTime updatedAt;

   @JsonManagedReference
   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Resume> resumes = new ArrayList<>();
}