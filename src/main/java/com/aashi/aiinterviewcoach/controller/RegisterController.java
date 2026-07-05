package com.aashi.aiinterviewcoach.controller;
import org.springframework.web.bind.annotation.*;
import com.aashi.aiinterviewcoach.entity.User;
import jakarta.validation.Valid;
import com.aashi.aiinterviewcoach.dto.RegisterRequest;
import com.aashi.aiinterviewcoach.service.RegisterService;
import com.aashi.aiinterviewcoach.dto.RegisterResponse;

@RestController
@RequestMapping("/api/auth")
public class RegisterController
{
  

    private RegisterService regser;



   
    @PostMapping("/register")
    public RegisterResponse Register(@Valid @RequestBody RegisterRequest res)
    {
        if(regser.checkemail(res.getEmail()))
        {
            return new RegisterResponse("user already register",res.getEmail());
        }
       return regser.register(res);
       
    }

}