package br.com.henriquesousa.musicapp.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.henriquesousa.musicapp.dto.UserLoginDTO;
import br.com.henriquesousa.musicapp.dto.UserRegisterDTO;
import br.com.henriquesousa.musicapp.entity.User;
import br.com.henriquesousa.musicapp.security.JWTUtil;
import br.com.henriquesousa.musicapp.service.UserService;
import br.com.henriquesousa.musicapp.service.exception.UserNotCreatedException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Map<String, Object> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        String encodedPass = passwordEncoder.encode(userRegisterDTO.getPassword());
        userRegisterDTO.setPassword(encodedPass);

        User user = new User();
        user.setUserName(userRegisterDTO.getUserName());
        user.setPassword(userRegisterDTO.getPassword());
        user.setName(userRegisterDTO.getName());
        // user.setEmail(userRegisterDTO.getEmail());
        try {
            userService.create(user);
            String token = jwtUtil.generateToken(user.getUserName());
            return Collections.singletonMap("jwt-token", token);
        } catch (UserNotCreatedException e) {
            return new HashMap<>();
        }

    }

    @PostMapping("/login")
    public Map<String, Object> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(userLoginDTO.getUserName(), userLoginDTO.getPassword());
            authenticationManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(userLoginDTO.getUserName());
            return Collections.singletonMap("jwt-token", token);
        } catch(AuthenticationException authExc) {
            throw new RuntimeException("Invalid username/password.");
        }

    }

}
