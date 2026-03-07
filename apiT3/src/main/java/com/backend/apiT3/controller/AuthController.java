package com.backend.apiT3.controller;

import com.backend.apiT3.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    // ENDPOINT 1
    @GetMapping("/")
    public Map<String,String> home(){

        return Map.of(
                "message","API funcionando"
        );
    }

    // ENDPOINT 2
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String,String> data){

        String username = data.get("username");
        String password = data.get("password");

        String result = authService.register(username,password);

        if(result.equals("Usuario ya existe")){

            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message",result));
        }

        return ResponseEntity
                .status(201)
                .body(Map.of("message",result));
    }

    // ENDPOINT 3
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> data){

        String username = data.get("username");
        String password = data.get("password");

        boolean success = authService.login(username,password);

        if(success){

            return ResponseEntity.ok(
                    Map.of(
                            "message","Login exitoso",
                            "username",username
                    )
            );
        }

        return ResponseEntity
                .status(401)
                .body(Map.of("message","Credenciales incorrectas"));
    }

}