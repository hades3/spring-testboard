package hades3.cleanboard.repository;

import hades3.cleanboard.domain.Member;
import hades3.cleanboard.domain.Post;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class PostRepository {

    private final EntityManager em;

    public Long save(Post post){
        em.persist(post);
        return post.getId();
    }

    public Post findOne(Long id){
        return em.find(Post.class, id);
    }

    public List<Post> findAll(){
        List<Post> posts = em.createQuery("select p from Post p", Post.class).getResultList();
        return posts;
    }

    public List<Post> findByTitle(String title){
        List<Post> posts = em.createQuery("select p from Post p where p.title like '%' + :title + '%'", Post.class).setParameter("title", title).getResultList();
        return posts;
    }

    public List<Post> findByWriter(String writer){
        List<Post> posts = em.createQuery("select p from Post p where p.title like '%' + :writer + '%'", Post.class).setParameter("writer", writer).getResultList();
        return posts;
    }

}
