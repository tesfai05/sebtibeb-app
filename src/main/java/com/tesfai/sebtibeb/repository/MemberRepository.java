package com.tesfai.sebtibeb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tesfai.sebtibeb.entity.Member;
@Repository
public interface MemberRepository extends PagingAndSortingRepository<Member, Integer> {
	
//	@Query("SELECT m FROM Member m WHERE CONCAT(m.firstName, m.middleName, m.lastName, m.address.state,m.gender,m.martialStatus) LIKE %?1%")
//	public List<Member> search(String keyword);
	
	@Query("SELECT m FROM Member m WHERE CONCAT(m.firstName, m.middleName, m.lastName, m.address.state,m.gender,m.martialStatus) LIKE %?1%")
	public Page<Member> search(Pageable pageable,String keyword);
	
}
