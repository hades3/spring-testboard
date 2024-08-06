package hades3.cleanboard.repository;

import hades3.cleanboard.domain.Comment;
import hades3.cleanboard.domain.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class CommentRepository {

    private final EntityManager em;

    public Long save(Comment comment){
        em.persist(comment);
        return comment.getId();
    }

    public Comment findOne(Long commentId){
        Comment findComment = em.find(Comment.class, commentId);
        return findComment;
    }

    public void removeOne(Long commentId){
        Comment findComment = findOne(commentId);
        em.remove(findComment);
    }

    public List<Comment> findByPostId(Long postId){
        return em.createQuery("select c from Comment c where c.post.id = :postId", Comment.class).setParameter("postId", postId).getResultList();
    }
}
