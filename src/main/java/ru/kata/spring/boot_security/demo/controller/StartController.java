package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class StartController {
    @GetMapping("/")
    public String printHello() {
        return "public/index";
    }
}
