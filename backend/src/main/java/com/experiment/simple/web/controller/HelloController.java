package com.experiment.simple.web.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "${WEB_APP_FRONTEND_URL:http://localhost:3000}")
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from backend!";
    }
}