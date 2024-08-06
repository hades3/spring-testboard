package hades3.cleanboard.controller;

import hades3.cleanboard.domain.Member;
import hades3.cleanboard.domain.MemberDto;
import hades3.cleanboard.service.MemberService;
import hades3.cleanboard.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Transactional
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/login")
    public String loginForm(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "member/loginForm";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute(name = "memberDto") MemberDto memberDto, HttpServletRequest request) {
        Member findMember = memberService.tryLogin(memberDto.getUsername(), memberDto.getPassword());
        if (findMember == null){
            return "member/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, findMember);
        return "redirect:/";
    }

    @GetMapping("/member/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/member/register")
    public String registerForm(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "member/registerForm";
    }

    @PostMapping("/member/register")
    public String register(@ModelAttribute(name = "memberDto") MemberDto memberDto){
        try{
            memberService.register(memberDto);
        }
        catch (IllegalStateException e){
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

}
