package hades3.cleanboard.repository;

import hades3.cleanboard.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findByName(String username){
        List<Member> findMembers = em.createQuery("select m from Member m where m.username = :username", Member.class).setParameter("username", username).getResultList();
        return findMembers;
    }

}
