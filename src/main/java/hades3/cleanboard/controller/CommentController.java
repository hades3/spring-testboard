package hades3.cleanboard.controller;

import hades3.cleanboard.domain.Member;
import hades3.cleanboard.service.CommentService;
import hades3.cleanboard.service.PostService;
import hades3.cleanboard.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Transactional
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/{postId}/new")
    public String writeComment(@PathVariable(name = "postId") Long postId, @RequestParam(name = "content") String content, HttpServletRequest request, @RequestParam(name = "redirect") String currentPageUrl){
        HttpSession session = request.getSession();

        Member curMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (curMember == null){
            System.out.println("로그인이 필요합니다.");
            return "redirect:/member/login";
        }

        commentService.write(curMember.getId(), postId, content);
        return "redirect:" + currentPageUrl;
    }

    @GetMapping("/comments/{commentId}/delete")
    public String deleteComment(@PathVariable(name = "commentId") Long commentId, HttpServletRequest request, @RequestParam(name = "redirect") String currentPageUrl){
        HttpSession session = request.getSession(false);
        Member curMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (curMember == null){
            System.out.println("로그인이 필요합니다");
            return "redirect:/member/login";
        }

        commentService.delete(commentId, curMember.getId());

        return "redirect:" + currentPageUrl;
    }
}