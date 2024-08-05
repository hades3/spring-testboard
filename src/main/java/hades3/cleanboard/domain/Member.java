package hades3.cleanboard.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    private String password;

    private String phoneNumber;

    private String address;

    public static Member createMember(MemberDto memberDto){
        Member member = new Member();
        member.setUsername(memberDto.getUsername());
        member.setPassword(memberDto.getPassword());
        member.setPhoneNumber(memberDto.getPhoneNumber());
        member.setAddress(memberDto.getAddress());
        return member;
    }
}
