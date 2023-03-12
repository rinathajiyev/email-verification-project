package com.company.controller;

import com.company.Entity.*;
import com.company.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.servlet.http.*;
import java.io.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String getHomePage(Model model){
        model.addAttribute("user", new User());
        return "index";
    }

    @GetMapping("/verify")
    public String verify(@RequestParam String code){
        if(userService.verify(code)){
            return "verify_success";
        } else{
            return "verify_fail";
        }
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@ModelAttribute("user") User user, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        userService.register(user, getSiteUrl(request));
        return "success";
    }

    private String getSiteUrl(HttpServletRequest request){
        String url = request.getRequestURL().toString();
        url = url.replace(request.getServletPath(), "");

        return url;
    }
}
