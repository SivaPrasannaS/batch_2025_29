package com.examly.springapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.MemberRequest;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Member;
import com.examly.springapp.repository.MemberRepository;
import com.examly.springapp.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member createMember(MemberRequest request) {
        Member member = new Member();
        member.setFullName(request.getFullName());
        member.setStudentId(request.getStudentId());
        member.setDepartment(request.getDepartment());
        member.setMaxBooksAllowed(request.getMaxBooksAllowed());
        return memberRepository.save(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
    }
}
