package dots.of.lots.category.notice.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dots.of.lots.category.notice.domain.Reply;
import dots.of.lots.category.notice.service.ReplyService;

@Controller
@RequestMapping("/reply")
public class ReplyController {

	@Autowired
	private ReplyService rService;

	@RequestMapping(value="/insert.do", method = RequestMethod.POST)
	public ModelAndView insertReply(ModelAndView mv
			, @ModelAttribute Reply reply
			, HttpSession session) {
		
		String url = "";
		try {
			String replyWriter = (String)session.getAttribute("memberId");		// 로그인 되어있는 아이디로 댓글 등록되게
			if(!reply.getReplyContent().equals("")) {
				if(!replyWriter.equals("")) {
					reply.setReplyWriter(replyWriter);
					int result = rService.insertReply(reply);
					url = "/notice/detail.do?noticeNo="+reply.getRefNoticeNo();
					if(result > 0) {
						mv.setViewName("redirect:"+url);
					} else {
						mv.addObject("msg", "댓글 등록이 완료되지 않았습니다.");
						mv.addObject("url", url);
						mv.setViewName("common/serviceResult");
					}
				} else {
					mv.addObject("msg", "로그인 정보가 확인되지 않았습니다.");
					mv.addObject("url", "/index.jsp");
					mv.setViewName("common/serviceResult");
				}
			} else {
				mv.addObject("msg", "글을 입력해주세요.");
				mv.addObject("url", url);
				mv.setViewName("common/serviceResult");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("url", url);
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	public ModelAndView updateReply(ModelAndView mv
			, @ModelAttribute Reply reply
			, HttpSession session) {

		String url = "";
		try {
			String replyWriter = (String)session.getAttribute("memberId");
			if(!replyWriter.equals("")) {
				reply.setReplyWriter(replyWriter);
				url = "/notice/detail.do?noticeNo="+reply.getRefNoticeNo();
				int result = rService.updateReply(reply);
				mv.setViewName("redirect:"+url);
			} else {
				mv.addObject("msg", "로그인 정보가 확인되지 않았습니다.");
				mv.addObject("url", "/index.jsp");
				mv.setViewName("common/serviceResult");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("url", url);
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.GET)
	public ModelAndView deleteReply(ModelAndView mv
			, @ModelAttribute Reply reply
			, HttpSession session) {	// 세션에서 아이디 가져오기
		// DELETE FROM REPLY_TBL WHERE REPLY_NO = 샵{ replyNo } AND R_STATUS = 'Y'
		// UPDATE REPLY_TBL SET R_STATUS = 'N' WHERE REPLY_NO = 샵{ replyNo }
		String url = "";
		try {
			String memberId = (String)session.getAttribute("memberId");
			String replyWriter = reply.getReplyWriter();
			url = "/notice/detail.do?noticeNo="+reply.getRefNoticeNo();
			if(replyWriter != null && replyWriter.equals(memberId)) {	// 세션 아이디, 작성자 비교
				int result = rService.deleteReply(reply);
				if(result > 0) {
					mv.setViewName("redirect:"+url);
				} else {
					mv.addObject("msg", "댓글 삭제가 완료되지 않았습니다.");
					mv.addObject("url", url);
					mv.setViewName("common/serviceResult");
				}
			} else {
				mv.addObject("msg", "자신의 댓글만 삭제 가능합니다.");
				mv.addObject("url", url);
				mv.setViewName("common/serviceResult");
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("url", url);
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
}
