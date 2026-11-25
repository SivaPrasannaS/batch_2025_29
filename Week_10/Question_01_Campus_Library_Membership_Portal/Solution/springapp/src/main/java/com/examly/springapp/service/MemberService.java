package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.MemberRequest;
import com.examly.springapp.model.Member;

public interface MemberService {

    Member createMember(MemberRequest request);

    List<Member> getAllMembers();

    Member getMemberById(Long id);
}
