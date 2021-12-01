package com.tesfai.sebtibeb.service;

import org.springframework.data.domain.Page;

import com.tesfai.sebtibeb.entity.Member;
import com.tesfai.sebtibeb.entity.User;
import com.tesfai.sebtibeb.exception.MemberNotFoundException;

public interface MemberService {
	
	public Member saveMember(Member member);

	public Page<Member> displayMembers(Integer pageNumber, String keyword, String sortField, String sortDirection);

	public Member editMember(Integer id) throws MemberNotFoundException;

	public void deleteMember(Integer id);

	public User getProfile(String username); 

}
