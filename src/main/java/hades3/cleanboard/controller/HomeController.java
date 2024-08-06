package hades3.cleanboard.controller;

import hades3.cleanboard.domain.Member;
import hades3.cleanboard.domain.Post;
import hades3.cleanboard.repository.PostRepository;
import hades3.cleanboard.service.PostService;
import hades3.cleanboard.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PostRepository postRepository;

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);

        HttpSession session = request.getSession();
//        if (session == null){
//            return "home";
//        }

        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

//        if (loginMember != null){
//            model.addAttribute("member", loginMember);
//        }
//        return "loginHome";

        if (loginMember == null){
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
