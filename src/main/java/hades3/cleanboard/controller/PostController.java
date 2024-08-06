package hades3.cleanboard.controller;

import hades3.cleanboard.domain.Member;
import hades3.cleanboard.domain.Post;
import hades3.cleanboard.domain.PostDto;
import hades3.cleanboard.repository.PostRepository;
import hades3.cleanboard.service.PostService;
import hades3.cleanboard.session.SessionConst;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Transactional
public class PostController {
    private final EntityManager em;
    private final PostRepository postRepository;
    private final PostService postService;

    @GetMapping("/posts/new")
    public String writeForm(Model model){
        model.addAttribute("postDto", new PostDto());
        return "post/writeForm";
    }

    @PostMapping("/posts/new")
    public String writePost(@ModelAttribute(name="postDto") PostDto postDto, HttpServletRequest request){
        HttpSession session = request.getSession();
        Member curMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        postService.write(postDto, curMember);
        return "redirect:/";
    }

    @GetMapping("/posts/{postId}")
    public String readPost(@PathVariable(name = "postId") Long postId, Model model){
        Post post = postService.read(postId);
        model.addAttribute("post", post);
        String currentPageUrl = "/posts/"+postId;
        model.addAttribute("currentPageUrl", currentPageUrl);
        return "post/readForm";
    }

    @GetMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable(name = "postId") Long postId, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null){
            System.out.println("로그인이 필요합니다");
            return "redirect:/member/login";
        }
        Member curMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (curMember == null){
            System.out.println("로그인이 필요합니다");
            return "redirect:/member/login";
        }
        postService.delete(postId, curMember.getId());

        return "redirect:/";
    }

    @GetMapping("/posts/{postId}/modify")
    public String modifyForm(@PathVariable(name = "postId") Long postId, HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if (session == null){
            System.out.println("로그인이 필요합니다");
            return "redirect:/member/login";
        }
        Member curMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (curMember == null){
            System.out.println("로그인이 필요합니다");
            return "redirect:/member/login";
        }
        if (postService.check(postId, curMember.getId())){
            Post post = postRepository.findOne(postId);
            model.addAttribute("post", post);
            return "post/modifyForm";
        }

        return "redirect:/";
    }

    @PostMapping("/posts/{postId}/modify")
    public String modifyPost(@PathVariable(name = "postId") Long postId, @ModelAttribute(name = "postDto") PostDto postDto){
        postService.update(postId, postDto.getTitle(), postDto.getContent());
        return "redirect:/";
    }
}
