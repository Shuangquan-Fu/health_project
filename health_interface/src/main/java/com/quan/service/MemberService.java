package com.quan.service;

import com.quan.pojo.Member;

public interface MemberService {
    public void add(Member member);
    public Member findByTelephone(String telephone);
}
