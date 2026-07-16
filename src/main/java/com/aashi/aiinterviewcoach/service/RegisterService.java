package com.aashi.aiinterviewcoach.service;
import com.aashi.aiinterviewcoach.dto.RegisterRequest;
import com.aashi.aiinterviewcoach.entity.User;
import com.aashi.aiinterviewcoach.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.aashi.aiinterviewcoach.entity.Role;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import com.aashi.aiinterviewcoach.dto.RegisterResponse;
import org.springframework.stereotype.Service;

@Service
public class RegisterService{
      @Autowired 
    private PasswordEncoder passwordencoder;
     @Autowired UserRepository userrepo;

    public RegisterResponse register(RegisterRequest res)
    {
        try{
            User user=new User();
       String name=res.getFullname();
       String email=res.getEmail();
       String password=passwordencoder.encode(res.getPassword());
       user.setFullname(name);
       user.setEmail(email);
       user.setPassword(password);
       user.setRole(Role.USER);
       user.setCreatedAt(LocalDateTime.now());
       user.setUpdatedAt(LocalDateTime.now());
       userrepo.save(user);
        }
        catch (Exception ex)
        {
            return new RegisterResponse(ex.getMessage(),res.getEmail());
        }
        return new RegisterResponse("user register successfully",res.getEmail());
    }
    public Boolean checkemail(String email)
    {
        return userrepo.existsByEmail(email);
    }
}