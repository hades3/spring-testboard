package hades3.cleanboard.service;

import hades3.cleanboard.domain.Member;
import hades3.cleanboard.domain.Post;
import hades3.cleanboard.domain.PostDto;
import hades3.cleanboard.repository.PostRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final EntityManager em;
    private final PostRepository postRepository;

    public Long write(PostDto postDto, Member member){
        Post post = Post.createPost(postDto, member);
        Long postId = postRepository.save(post);
        return postId;
    }

    public Post read(Long postId){
        Post findPost = postRepository.findOne(postId);
        findPost.setViews(findPost.getViews()+1);
        em.flush();
        return findPost;
    }
}
