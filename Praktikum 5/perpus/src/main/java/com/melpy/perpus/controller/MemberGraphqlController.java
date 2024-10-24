package com.melpy.perpus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.melpy.perpus.entity.Member;
import com.melpy.perpus.repository.MemberRepository;

@Controller
public class MemberGraphqlController {
    @Autowired
    private MemberRepository memberRepository;

    @QueryMapping
    public List<Member> members() {
        return (List<Member>) memberRepository.findAll();
    }

    @QueryMapping
    public Member memberById(@Argument Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member tidak ditemukan"));
    }

    @MutationMapping
    public Member createMember(@Argument String memberID,
            @Argument String name,
            @Argument String address,
            @Argument String phoneNumber) {
        Member member = Member.builder()
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .memberID(memberID)
                .build();
        return memberRepository.save(member);
    }

    @MutationMapping
    public Member updateMember(@Argument Long id, @Argument String memberID, @Argument String name,
            @Argument String address,
            @Argument String phoneNumber) {
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member tidak ditemukan"));
        if (name != null)
            existMember.setName(name);
        if (address != null)
            existMember.setAddress(address);
        if (phoneNumber != null)
            existMember.setPhoneNumber(phoneNumber);
        if (memberID != null)
            existMember.setMemberID(memberID);
        return memberRepository.save(existMember);
    }

    @MutationMapping
    public Member deleteMember(@Argument long id) {
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member tidak ditemukan"));
        memberRepository.delete(existMember);
        return existMember;
    }
}
