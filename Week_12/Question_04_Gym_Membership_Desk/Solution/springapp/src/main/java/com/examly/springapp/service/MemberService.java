package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Member;

public interface MemberService {

    List<Member> getAllMembers();

    Member addMember(Member member);
}
