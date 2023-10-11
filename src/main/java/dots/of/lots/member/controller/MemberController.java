package dots.of.lots.member.controller;

import java.util.regex.Pattern;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dots.of.lots.member.domain.Member;
import dots.of.lots.member.service.MemberService;

@Controller
@SessionAttributes({"memberId", "memberName"})
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@RequestMapping(value="/member/register.do", method=RequestMethod.GET)
	public ModelAndView showRegisterForm(ModelAndView mv) {
		mv.setViewName("member/sign");
		return mv;
	}
	
	@RequestMapping(value="/member/register.do", method=RequestMethod.POST)
	public ModelAndView insertMember(ModelAndView mv
			, @ModelAttribute Member mOne) {
		
		Member member = this.checkLoginItems(mOne);
		
		try {
			if(member != null) {
				int result = service.insertMember(member);
				if(result > 0) {
					mv.setViewName("redirect:/member/login.do");
				} else {
					mv.addObject("msg", "회원가입이 완료되지 않았습니다.");
					mv.addObject("url", "/member/login.do");
					mv.setViewName("common/serviceResult");
				}
			} else {
				mv.addObject("msg", "정보를 정확히 입력해주세요.");
				mv.addObject("url", "/member/login.do");
				mv.setViewName("common/serviceResult");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("url", "/member/login.do");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	public Member checkLoginItems(Member mOne) {
		boolean regex = true;
			
		if(!mOne.getMemberPw().equals(mOne.getMemberPwCheck()) ) {
			// 비밀번호가 비밀번호 체크와 같은지 확인
			mOne = null;
			return mOne;
			
		} else if(mOne.getMemberPw() == null || "".equals(mOne.getMemberPw())) {
			// 비밀번호가 null 인지, 띄어쓰기가 있는지
			mOne = null;
			return mOne;
			
		} else if(regex = Pattern.matches("^[a-z0-9]*$", mOne.getMemberId())) {
			// 아이디에 영어, 숫자만 넣었는지
			if(regex = true) {
				return mOne;
			} else {
				mOne = null;
				return mOne;
			}
			
		} else if(regex = Pattern.matches("^01(?:0|1|[6-9])-(?:\\\\d{3}|\\\\d{3})-\\\\d{4}$", mOne.getMemberPhone())) {
			// 핸드폰번호 확인
			if(regex = true) {
				return mOne;
			} else {
				mOne = null;
				return mOne;
			}
			
		} else if(regex = Pattern.matches("^[a-zA-z0-9]+@[a-zA-Z0-9]+\\\\.[a-z]+$", mOne.getMemberEmail())) {
			// 이메일 확인
			if(regex = true) {
				return mOne;
			} else {
				mOne = null;
				return mOne;
			}
		}
		return mOne;
	}
	
	@RequestMapping(value="/member/myInfo.do", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showMypageForm(ModelAndView mv
			, HttpSession session) {
		
		try {
			String memberId = (String)session.getAttribute("memberId");
			Member member = null;
			if(memberId != "" && memberId != null) {
				member = service.selectOneById(memberId);
			}
			
			if(member != null) {
				mv.addObject("member", member);
				mv.setViewName("member/myInfo");
			} else {
				mv.addObject("msg", "조회가 완료되지 않았습니다.");
				mv.addObject("url", "/member/findPw.do");
				mv.setViewName("common/serviceResult");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("url", "/index.jsp");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}

	@RequestMapping(value="/member/update.do", method=RequestMethod.GET)
	public ModelAndView showUpdateView(ModelAndView mv
			, @RequestParam("memberId") String memberId) {
		
		try {
			Member member = service.selectOneById(memberId);
			
			if(member != null) {
				mv.addObject("member", member);
				mv.setViewName("member/myInfo");
			} else {
				mv.addObject("msg", "조회가 완료되지 않았습니다.");
				mv.addObject("url", "/member/update.do");
				mv.setViewName("common/serviceResult");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("url", "/index.jsp");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	@RequestMapping(value="/member/update.do", method=RequestMethod.POST)
	public ModelAndView updateMember(ModelAndView mv
			, @ModelAttribute Member member
			, @RequestParam("memberId") String memberId) {
		
		try {
			Member mOne = service.selectOneById(memberId);
			if(member.getMemberPw().equals(member.getMemberPwCheck()) && member.getMemberPw().equals(mOne.getMemberPw())) {
				int result = service.updateMember(member);
				if(result > 0) {
					mv.addObject("msg", "업데이트 성공");
					mv.addObject("url", "/member/myInfo.do");
					mv.setViewName("common/serviceResult");
				} else {
					mv.addObject("msg", "업데이트 실패");
					mv.addObject("url", "/member/update.do");
					mv.setViewName("common/serviceResult");
				}
			} else {
				mv.addObject("msg", "비밀번호가 다릅니다.");
				mv.addObject("url", "/member/update.do");
				mv.setViewName("common/serviceResult");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("url", "/index.jsp");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	@RequestMapping(value="/member/delete.do", method=RequestMethod.GET)
	public ModelAndView deleteMember(ModelAndView mv
			, @RequestParam("memberId") String memberId
			, @ModelAttribute Member member) {
		
		try {
			Member mOne = service.selectOneById(memberId);
			if(member.getMemberPw().equals(member.getMemberPwCheck()) && member.getMemberPw().equals(mOne.getMemberPw())) {
				int result = service.deleteMember(memberId);
				if(result > 0) {
					mv.setViewName("redirect:/member/logout.do");
				} else {
					mv.addObject("msg", "회원삭제에 실패하였습니다.");
					mv.addObject("url", "/member/myInfo.do");
					mv.setViewName("common/serviceResult");
				}
			} else {
				mv.addObject("msg", "비밀번호가 다릅니다.");
				mv.addObject("url", "/member/update.do");
				mv.setViewName("common/serviceResult");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("url", "/index.jsp");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	@RequestMapping(value="/member/login.do", method=RequestMethod.GET)
	public ModelAndView showLoginForm(ModelAndView mv) {
		mv.setViewName("member/login");
		return mv;
	}
	
	@RequestMapping(value="/member/login.do", method=RequestMethod.POST)
	public ModelAndView memberLogin(ModelAndView mv
			, @ModelAttribute Member member) {
		
		try {
			Member mOne = service.selectCheckLogin(member);
			
			if(mOne != null) {
				mv.addObject("memberId", mOne.getMemberId());
				mv.addObject("memberName", mOne.getMemberName());
				mv.setViewName("redirect:/index.jsp");
			} else {
				mv.addObject("msg", "로그인이 완료되지 않았습니다.");
				mv.addObject("url", "/member/login.do");
				mv.setViewName("common/serviceResult");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("url", "/index.jsp");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	@RequestMapping(value="/member/logout.do", method=RequestMethod.GET)
	public ModelAndView memberLogout(ModelAndView mv
			, HttpSession sessionPrev
			, SessionStatus session) {
		
		if(session != null) {
			session.setComplete(); 		// 만료 시킴
			if(session.isComplete()) {	// 만료 되었는지 체크
				mv.setViewName("redirect:/member/login.do");
			}
		} else {
			mv.addObject("msg", "로그아웃에 실패하였습니다.");
			mv.addObject("url", "/member/myInfo.do");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	@RequestMapping(value="/member/findId.do", method=RequestMethod.GET)
	public ModelAndView showFindIdForm(ModelAndView mv) {
		mv.setViewName("member/finding-Id");
		return mv;
	}
	
	@RequestMapping(value="/member/findId.do", method=RequestMethod.POST)
	public ModelAndView findId(ModelAndView mv
			, @RequestParam("memberEmail") String memberEmail
			, @RequestParam("memberPhone") String memberPhone) {
		
		try {
			Member member = new Member();
			member.setMemberEmail(memberEmail);
			member.setMemberPhone(memberPhone);
			
			Member mOne = service.findId(member);
			
			if(mOne != null) {
				mv.addObject("member", mOne);
				mv.addObject("msg", "아이디는 " + mOne.getMemberId() + " 입니다.");
				mv.addObject("url", "/member/login.do");
				mv.setViewName("common/serviceResult");
			} else {
				mv.addObject("msg", "조회가 완료되지 않았습니다.");
				mv.addObject("url", "/member/findId.do");
				mv.setViewName("common/serviceResult");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("url", "/index.jsp");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	@RequestMapping(value="/member/findPw.do", method=RequestMethod.GET)
	public ModelAndView showFindPwForm(ModelAndView mv) {
		mv.setViewName("member/finding-Pw");
		return mv;
	}
	
	@RequestMapping(value="/member/findPw.do", method=RequestMethod.POST)
	public ModelAndView findPw(ModelAndView mv
			, @RequestParam("memberId") String memberId
			, @RequestParam("memberPhone") String memberPhone) {
		
		try {
			Member member = new Member();
			member.setMemberId(memberId);
			member.setMemberPhone(memberPhone);
			
			Member mOne = service.findPw(member);
			
			if(mOne != null) {
				mv.addObject("member", mOne);
				mv.addObject("msg", "비밀번호는 " + mOne.getMemberPw() + " 입니다.");
				mv.addObject("url", "/member/login.do");
				mv.setViewName("common/serviceResult");
			} else {
				mv.addObject("msg", "조회가 완료되지 않았습니다.");
				mv.addObject("url", "/member/findPw.do");
				mv.setViewName("common/serviceResult");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("url", "/index.jsp");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
}
