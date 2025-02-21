package com.example.learnSpring.controllers.authenticatorC;


import com.example.learnSpring.dtos.authenticationtDto.AuthenticationRecordDTO;
import com.example.learnSpring.dtos.loginDTO.LoginRecordDTO;
import com.example.learnSpring.dtos.registerDto.RegisterRecordDTO;
import com.example.learnSpring.handlers.exceptions.UserExistsException;
import com.example.learnSpring.infra.security.TokenService;
import com.example.learnSpring.models.users.User;
import com.example.learnSpring.repositories.userR.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticatorController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationRecordDTO data){
        var tokenUser = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(tokenUser);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginRecordDTO(token));
    }


    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRecordDTO data){
        if (this.userRepository.findByLogin(data.login()) != null){
            throw new UserExistsException();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
