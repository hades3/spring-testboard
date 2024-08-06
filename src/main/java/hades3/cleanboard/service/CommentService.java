package hades3.cleanboard.service;

import hades3.cleanboard.domain.Comment;
import hades3.cleanboard.domain.Member;
import hades3.cleanboard.domain.Post;
import hades3.cleanboard.repository.CommentRepository;
import hades3.cleanboard.repository.MemberRepository;
import hades3.cleanboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public Long write(Long memberId, Long postId, String content){
        Member findMember = memberRepository.findOne(memberId);
        Post findPost = postRepository.findOne(postId);

        Comment comment = new Comment();
        LocalDateTime now = LocalDateTime.now();
        comment.setMember(findMember);
        comment.setPost(findPost);
        comment.setContent(content);
        comment.setCreatedDate(now);
        comment.setModifiedDate(now);

        Long commentId = commentRepository.save(comment);
        return commentId;
    }

    public void delete(Long commentId, Long memberId){
        Comment findComment = commentRepository.findOne(commentId);
        if (check(commentId, memberId)){
            commentRepository.removeOne(commentId);
        }
        else{
            System.out.println("작성자가 아니므로 지울 수 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public boolean check(Long commentId, Long memberId){
        Comment findComment = commentRepository.findOne(commentId);
        if (findComment == null){
            return false;
        }
        if (findComment.getMember().getId().equals(memberId)){
            return true;
        }
        else{
            return false;
        }
    }
}
