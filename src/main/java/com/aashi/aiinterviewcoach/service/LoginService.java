package com.aashi.aiinterviewcoach.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aashi.aiinterviewcoach.dto.LoginRequest;
import com.aashi.aiinterviewcoach.dto.LoginResponse;
import com.aashi.aiinterviewcoach.entity.User;
import com.aashi.aiinterviewcoach.repository.UserRepository;

@Service
public class LoginService {

    private final UserRepository userrepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwt;

    public LoginService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
        this.userrepo = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        

        return new LoginResponse("Login Successful");
    }
}