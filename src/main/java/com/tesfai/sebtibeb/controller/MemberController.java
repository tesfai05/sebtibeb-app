package com.tesfai.sebtibeb.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tesfai.sebtibeb.entity.Member;
import com.tesfai.sebtibeb.entity.User;
import com.tesfai.sebtibeb.exception.MemberNotFoundException;
import com.tesfai.sebtibeb.service.MemberService;
import com.tesfai.sebtibeb.service.UserService;

@Controller
@SessionAttributes(names = "user")
public class MemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private UserService userService;
	
	@GetMapping(value = { "/public/register" })
	public String addNewMember(Model model) {
		model.addAttribute("member", new Member());
		return "member/newMember";		
	}
	
	@PostMapping(value = { "/public/register" })
	@Transactional
	public String saveNewMember(Model model, @Valid @ModelAttribute("member") Member member, BindingResult result) {
		model.addAttribute("member", member);
		if (result.hasErrors()) {
			return "member/newMember";
		}
		memberService.saveMember(member);
		
		userService.save(new User(null,
				member.getUsername(),
				member.getPassword(),
				"ROLE_USER",
				false)
			);
		return "redirect:/member/list";
	}
	
	@GetMapping(value = { "/member/list" })
	public String displayMembers(Model model,@Param("keyword")String keyword, Principal principal) {	
		       model.addAttribute("user", principal.getName());
		return displayMembersByPage(model,1,keyword,"firstName","asc");
	}
	
	@GetMapping(value = { "/members/page/{pageNumber}" })
	public String displayMembersByPage(Model model,
			@PathVariable("pageNumber")Integer currentPage,
			@Param("keyword")String keyword,
			@Param("sortField")String sortField,
			@Param("sortDirection")String sortDirection) {
		
		Page<Member> pageableMembers = memberService.displayMembers(currentPage, keyword, sortField, sortDirection);
		
		long totalMembers = pageableMembers.getTotalElements();
		int totalPages = pageableMembers.getTotalPages();		
		
		List<Member> members = pageableMembers.getContent();
		
		model.addAttribute("members", members);
		model.addAttribute("memberCount", members.size());
		keyword = keyword==null?"":keyword;
        model.addAttribute("keyword", keyword);
        
        model.addAttribute("totalMembers", totalMembers);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        
        String reverseSortDirection = sortDirection.equalsIgnoreCase("asc")?"desc":"asc";
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", reverseSortDirection);
        
		return "member/list";
	}
	
	@GetMapping(value = "/editmember/{id}")
	public String editMember(@PathVariable Integer id, Model model){
		Member member=null;
		try {
			member = memberService.editMember(id);
		} catch (MemberNotFoundException e) {
			model.addAttribute("msg", e.getMessage());
			return "redirect:/exception";
		}
		model.addAttribute("member", member);
		return "member/newMember";
		
	}
	
	@GetMapping(value = "/deletemember/{id}")
	public String deleteMember(@PathVariable Integer id, Model model) {
		memberService.deleteMember(id);
		return "redirect:/member/list";
	}
	
	@GetMapping(value = "/member/profile")
	public String memberProfile(Model model, Principal principal) {
		String username = principal.getName();
		model.addAttribute("profile", memberService.getProfile(username));
		return "member/profile";
	}
	
}
