package hades3.cleanboard.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Post extends Date {

    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    private String title;

    private String password;

    private String content;

    private int views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    public static Post createPost(PostDto postDto, Member member){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setPassword(postDto.getPassword());
        post.setContent(postDto.getContent());
        post.setViews(0);
        LocalDateTime now = LocalDateTime.now();
        post.setCreatedDate(now);
        post.setModifiedDate(now);
        post.setMember(member);
        return post;
    }
}
