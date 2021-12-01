package com.tesfai.sebtibeb.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tesfai.sebtibeb.entity.User;
import com.tesfai.sebtibeb.service.UserService;


@Controller
@SessionAttributes(names = "user")
public class HomePageController {
	@Autowired
	private UserService userService;
	
	@GetMapping(value = {"/","/public/"})
	public String displayHomePage(Model model, Principal principal) {
		String principalName = principal==null?StringUtils.EMPTY:principal.getName();
		model.addAttribute("user", principalName);
		return "home/index";
	}
	
	@GetMapping("/public/create-account")
	public String displayCreateAccount(Model model) {
		model.addAttribute("userAccount", new User());
		return "home/create-account";
	}
	
	@PostMapping("/public/create-account")
	public String processCreateAccount(Model model, @Valid @ModelAttribute("userAccount") User user,BindingResult result) {	
		model.addAttribute("userAccount", user);
		if(result.hasErrors()) {
			return "home/create-account";
		}
		User availableUser = userService.checkUserAvailability(user.getUsername());
		if(availableUser!=null) {
			model.addAttribute("usernameExist", user.getUsername()+" already taken !!");
			return "home/create-account";
		}
		user.setRole("ROLE_USER");
		userService.save(user);	
		return "redirect:/public/member/login";
	}

	@GetMapping("/public/information")
	public String informationDisplay(Model model) {
		return "home/info";
	}
	
	@GetMapping("/public/contacts")
	public String displayContatcts(Model model) {
		return "home/contact";
	}
	
	@GetMapping("/public/exception")
	public String errorDisplay(Model model) {
		return "home/error";
	}
	
	@GetMapping("/public/under-construction2")
	public String underConstruction2(Model model) {
		return "home/underconstruction";
	}

}
