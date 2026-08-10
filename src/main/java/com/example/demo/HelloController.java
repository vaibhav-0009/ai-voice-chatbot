package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "index.html";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "AI Chatbot Backend Running";
    }
}
