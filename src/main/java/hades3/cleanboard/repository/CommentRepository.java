package hades3.cleanboard.repository;

import hades3.cleanboard.domain.Comment;
import hades3.cleanboard.domain.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public Long save(Comment comment){
        em.persist(comment);
        return comment.getId();
    }
}
