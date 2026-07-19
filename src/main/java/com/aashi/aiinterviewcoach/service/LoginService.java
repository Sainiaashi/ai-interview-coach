package com.aashi.aiinterviewcoach.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aashi.aiinterviewcoach.dto.LoginRequest;
import com.aashi.aiinterviewcoach.dto.LoginResponse;
import com.aashi.aiinterviewcoach.entity.User;
import com.aashi.aiinterviewcoach.repository.UserRepository;
import com.aashi.aiinterviewcoach.service.JwtService;


@Service
public class LoginService {

    private final UserRepository userrepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwt;
    


    public LoginService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,JwtService jwt) {
        this.userrepo = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwt=jwt;
    }

    public LoginResponse login(LoginRequest loreq) {

        Optional<User> user = userrepo.findByEmail(loreq.getEmail());

        if (user.isEmpty()) {
            return new LoginResponse("Invalid Email");
        }

        User existingUser = user.get();

        if (!passwordEncoder.matches(loreq.getPassword(), existingUser.getPassword())) {
            return new LoginResponse("Invalid Password");
        }
        String email=existingUser.getEmail();
        String Role=existingUser.getRole().name();
        String token=jwt.generateKey(email,Role);

        return new LoginResponse("Login Successful\n token:"+token);
    }
}