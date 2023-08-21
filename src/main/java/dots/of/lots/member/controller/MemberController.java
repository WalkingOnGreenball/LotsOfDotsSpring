package dots.of.lots.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dots.of.lots.member.domain.Member;
import dots.of.lots.member.service.MemberService;

@Controller
@SessionAttributes({"memberId", "memberName"})
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	
	@RequestMapping(value="/member/register.do", method=RequestMethod.GET)
	public String showRegisterForm(Model model) {
		return "member/sign";
	}
	
	@RequestMapping(value="/member/register.do", method=RequestMethod.POST)
	public String insertMember(
			@ModelAttribute Member member
			, Model model) {
		
		try {
			int result = service.insertMember(member);
			
			if(result > 0) {
				model.addAttribute("msg", "회원가입에 성공했어요.");
				model.addAttribute("url", "/member/login.do");
				return "common/serviceResult";
			} else {
				model.addAttribute("msg", "회원가입이 완료되지 않았습니다.");
				model.addAttribute("url", "/member/login.do");
				return "common/serviceResult";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/serviceResult";
		}
	}
	
	
	@RequestMapping(value="/member/update.do", method=RequestMethod.GET)
	public String showUpdateView(
			@RequestParam("memberId") String memberId
			,Model model) {
		
		try {
			Member member = service.selectOneById(memberId);
			
			if(member != null) {
				model.addAttribute("member", member);
				return "member/myInfo";
			} else {
				model.addAttribute("msg", "조회가 완료되지 않았습니다.");
				model.addAttribute("url", "/member/update.do");
				return "common/serviceResult";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/serviceResult";
		}
	}
	
	@RequestMapping(value="/member/update.do", method=RequestMethod.POST)
	public String updateMember(
			@ModelAttribute Member member
			, RedirectAttributes redirect
			, Model model) {
		
		try {
			int result = service.updateMember(member);
			
			if(result > 0) {
				redirect.addAttribute("memberId", member.getMemberId());
				model.addAttribute("msg", "업데이트 성공");
				model.addAttribute("url", "/member/myInfo.do?memberId="+member.getMemberId());
				return "common/serviceResult";
			} else {
				model.addAttribute("msg", "업데이트 실패");
				model.addAttribute("url", "/member/myInfo.do");
				return "common/serviceResult";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/serviceResult";
		}
		
	}
	
	
	@RequestMapping(value="/member/delete.do", method=RequestMethod.GET)
	public String deleteMember(
			@RequestParam("memberId") String memberId
			, Model model) {
		
		try {
			int result = service.deleteMember(memberId);
			
			if(result > 0) {
				return "redirect:/member/logout.do";
			} else {
				model.addAttribute("msg", "회원삭제에 실패하였습니다.");
				model.addAttribute("url", "/member/myInfo.do");
				return "common/serviceResult";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/serviceResult";
		}
	}
	
	
	@RequestMapping(value="/member/login.do", method=RequestMethod.GET)
	public String showLoginForm(Model model) {
		return "member/login";
	}
	
	@RequestMapping(value="/member/login.do", method=RequestMethod.POST)
	public String memberLogin(
			@RequestParam("memberId") String memberId
			, @RequestParam("memberPw") String memberPw
			, Model model) {
		
		try {
			Member member = new Member(memberId, memberPw);
			Member mOne = service.selectCheckLogin(member);
			
			if(mOne != null) {
				model.addAttribute("memberId", mOne.getMemberId());
				model.addAttribute("memberName", mOne.getMemberName());
				
				model.addAttribute("msg", "로그인 성공!");
				model.addAttribute("url", "/member/myInfo.do?memberId="+memberId);
				return "common/serviceResult";
			} else {
				model.addAttribute("msg", "로그인에 실패하였습니다.");
				model.addAttribute("url", "/member/login.do");
				return "common/serviceResult";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/serviceResult";
		}
	}
	
	
	@RequestMapping(value="/member/logout.do", method=RequestMethod.GET)
	public String memberLogout(
			HttpSession sessionPrev
			, SessionStatus session
			, Model model) {
		
		if(session != null) {
			session.setComplete();
			if(session.isComplete()) {
				// 세션 만료 유효성 체크
			}
			return "redirect:/member/login.do";
		} else {
			model.addAttribute("msg", "로그아웃에 실패하였습니다.");
			model.addAttribute("url", "/member/login.do");
			return "common/serviceResult";
		}
	}
	
	
	@RequestMapping(value="/member/findId.do", method=RequestMethod.GET)
	public String showFindIdForm(Model model) {
		return "member/finding-Id";
	}
	
	@RequestMapping(value="/member/findId.do", method=RequestMethod.POST)
	public String findId(
			@RequestParam("memberEmail") String memberEmail
			, @RequestParam("memberPhone") String memberPhone
			, Model model) {
		
		try {
			Member member = new Member();
			member.setMemberEmail(memberEmail);
			member.setMemberPhone(memberPhone);
			
			Member mOne = service.findId(member);
			
			if(mOne != null) {
				model.addAttribute("member", mOne);

				model.addAttribute("msg", "아이디는 " + mOne.getMemberId() + " 입니다.");
				model.addAttribute("url", "/member/login.do");
				return "common/serviceResult";
			} else {
				model.addAttribute("msg", "조회가 완료되지 않았습니다.");
				model.addAttribute("url", "/member/findId.do");
				return "common/serviceResult";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/serviceResult";
		}
	}
	
	
	@RequestMapping(value="/member/findPw.do", method=RequestMethod.GET)
	public String showFindPwForm(Model model) {
		return "member/finding-Pw";
	}
	
	@RequestMapping(value="/member/findPw.do", method=RequestMethod.POST)
	public String findPw(
			@RequestParam("memberId") String memberId
			, @RequestParam("memberPhone") String memberPhone
			, Model model) {
		
		try {
			Member member = new Member();
			member.setMemberId(memberId);
			member.setMemberPhone(memberPhone);
			
			Member mOne = service.findPw(member);
			
			if(mOne != null) {
				model.addAttribute("member", mOne);

				model.addAttribute("msg", "비밀번호는 " + mOne.getMemberPw() + " 입니다.");
				model.addAttribute("url", "/member/login.do");
				return "common/serviceResult";
			} else {
				model.addAttribute("msg", "조회가 완료되지 않았습니다.");
				model.addAttribute("url", "/member/findPw.do");
				return "common/serviceResult";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/serviceResult";
		}
	}
	
	
	@RequestMapping(value="/member/myInfo.do", method=RequestMethod.GET)
	public String showDetailMember(
			@RequestParam("memberId") String memberId
			, Model model) {
		
		try {
			Member member = service.selectOneById(memberId);
			
			if(member != null) {
				model.addAttribute("member", member);
				return "member/myInfo";
				
			} else {
				model.addAttribute("msg", "조회가 완료되지 않았습니다.");
				model.addAttribute("url", "/member/login.do");
				return "common/serviceResult";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/serviceResult";
		}
	}
	
}
