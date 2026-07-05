package com.aashi.aiinterviewcoach.entity;
import com.aashi.aiinterviewcoach.entity.Role;
import jakarta.persistence.*;
import java.util.*;
import java.time.LocalDateTime;
import lombok.*;

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

   private LocalDateTime createdAt;
   private LocalDateTime updatedAt; 
}