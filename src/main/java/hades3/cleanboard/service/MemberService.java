package hades3.cleanboard.service;

import hades3.cleanboard.domain.Member;
import hades3.cleanboard.domain.MemberDto;
import hades3.cleanboard.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member tryLogin(String username, String password){
        List<Member> findMembers = memberRepository.findByName(username);
        if (findMembers.isEmpty()){
            return null;
        }
        else{
            Member findMember = findMembers.get(0);
            if (findMember.getPassword().equals(password)){
                return findMember;
            }
            else{
                return null;
            }
        }
    }

    public Long register(MemberDto memberDto){
        Member member = Member.createMember(memberDto);
        Long memberId = memberRepository.save(member);
        return memberId;
    }
}
