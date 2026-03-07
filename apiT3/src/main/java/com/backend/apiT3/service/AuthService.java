package com.backend.apiT3.service;

import com.backend.apiT3.model.User;
import com.backend.apiT3.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String register(String username, String password) {

        if(userRepository.findByUsername(username).isPresent()){
            return "Usuario ya existe";
        }

        String hash = encoder.encode(password);

        User user = new User(username, hash);

        userRepository.save(user);

        return "Usuario registrado";
    }

    public boolean login(String username, String password){

        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()){
            return false;
        }

        return encoder.matches(password, user.get().getPassword());
    }

}