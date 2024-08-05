package hades3.cleanboard.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Comment extends Date {

    @Id
    @GeneratedValue
    @Column(name = "COMMENT_ID")
    private Long id;

    private String writer;

    private String password;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

}
