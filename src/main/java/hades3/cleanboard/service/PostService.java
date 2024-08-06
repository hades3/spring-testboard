package hades3.cleanboard.service;

import hades3.cleanboard.domain.Member;
import hades3.cleanboard.domain.Post;
import hades3.cleanboard.domain.PostDto;
import hades3.cleanboard.repository.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        return findPost;
    }

    public void delete(Long postId, Long memberId){
        if (check(postId, memberId)){
            postRepository.removeOne(postId);
        }
        else{
            System.out.println("작성자가 아니므로 지울 수 없습니다.");
        }
    }

    public Post update(Long id, String title, String content){
        Post findPost = postRepository.findOne(id);
        findPost.setTitle(title);
        findPost.setContent(content);
        findPost.setModifiedDate(LocalDateTime.now());
        return findPost;
    }

    @Transactional(readOnly = true)
    public boolean check(Long postId, Long memberId){
        Post findPost = postRepository.findOne(postId);
        if (findPost.getMember().getId().equals(memberId)){
            return true;
        }
        else{
            return false;
        }
    }


}
