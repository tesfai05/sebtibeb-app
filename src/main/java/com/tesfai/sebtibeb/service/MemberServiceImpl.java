package com.tesfai.sebtibeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.tesfai.sebtibeb.entity.Member;
import com.tesfai.sebtibeb.entity.User;
import com.tesfai.sebtibeb.exception.MemberNotFoundException;
import com.tesfai.sebtibeb.repository.MemberRepository;
import com.tesfai.sebtibeb.repository.UserRepository;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private UserRepository userRepository;
	@Override
	public Member saveMember(Member member) {

		return memberRepository.save(member);
		
	}

	@Override
	public Page<Member> displayMembers(Integer pageNumber,String keyword, String sortField, String sortDirection) {
		Sort sort = Sort.by(sortField);
		sort=sortDirection.equalsIgnoreCase("asc")?sort.ascending():sort.descending();
		pageNumber = pageNumber==0?1:pageNumber;
		Pageable pageable = PageRequest.of(pageNumber-1, 5, sort);

		if (keyword != null||!StringUtils.isEmpty(keyword)) {
            return memberRepository.search(pageable,keyword);
        }		
		
		return memberRepository.findAll(pageable);
	}

	@Override
	public Member editMember(Integer id) throws MemberNotFoundException {
		return memberRepository.findById(id)
				.orElseThrow(()-> new MemberNotFoundException("No member with id - "+id+" is found."));
	}

	@Override
	public void deleteMember(Integer id) {
		memberRepository.deleteById(id); 
		
	}

	@Override
	public User getProfile(String username) {
		return userRepository.findUserByUsername(username);
		
	}

}
