package com.tesfai.sebtibeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tesfai.sebtibeb.entity.User;

@Controller
public class LoginController {
	@GetMapping("/public/member/login")
	public String loginPage(Model model) {
		model.addAttribute("user-account", new User());
		return "member/login";
	}
	
	@PostMapping("/login_success_handler")
    public String loginSuccessHandler() {
        System.out.println("Logging user login success...");
        return "redirect:/";
    }
	
	@PostMapping("/login_failure_handler")
	public String loginFailureHandler() {
	    System.out.println("Login failure handler....");     
	    return "redirect:/public/member/login";
	}
	
	@PostMapping("/login_error")
	public String loginErrorHandler() {
	    System.out.println("Login failure handler....");	     
	    return "redirect:/public/member/login";
	}
}
