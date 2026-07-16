package com.aashi.aiinterviewcoach.controller;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.aashi.aiinterviewcoach.dto.RegisterRequest;
import com.aashi.aiinterviewcoach.service.RegisterService;
import com.aashi.aiinterviewcoach.dto.RegisterResponse;
import com.aashi.aiinterviewcoach.dto.LoginResponse;
import com.aashi.aiinterviewcoach.dto.LoginRequest;
import com.aashi.aiinterviewcoach.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/auth")
public class RegisterController
{
   @Autowired
   private RegisterService regser;
   @Autowired
   private LoginService loser;

   @PostMapping("/register")
    public RegisterResponse Register(@Valid @RequestBody RegisterRequest res)
    {
        if(regser.checkemail(res.getEmail()))
        {
            return new RegisterResponse("user already register",res.getEmail());
        }
       return regser.register(res);
       
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest logreq)
    {
       return loser.login(logreq);
    }

}