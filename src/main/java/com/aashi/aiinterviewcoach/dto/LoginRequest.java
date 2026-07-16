package com.aashi.aiinterviewcoach.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest{

    @NotBlank(message="email not be empty")
    @Email(message="invalid email")
    private String email;

    @Size(min=6,message="password must have atleast 6 character")
    private String password;
}