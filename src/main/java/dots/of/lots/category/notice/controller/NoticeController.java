package dots.of.lots.category.notice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dots.of.lots.category.notice.domain.Notice;
import dots.of.lots.category.notice.domain.PageInfo;
import dots.of.lots.category.notice.service.NoticeService;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService service;
	
	
	@RequestMapping(value="/notice/list.do", method=RequestMethod.GET)
	public String selectNoticeList(
			@RequestParam(value="currentPage", required=false, defaultValue="1") Integer currentPage
			, Model model) {
		try {
			int totalCount = service.getListCount();
			PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
			String result = this.getNaviInfo(currentPage, totalCount);
			
			List<Notice> nList = service.selectNoticeList(pInfo);
			
			if(!nList.isEmpty()) {
				model.addAttribute("pInfo", pInfo);
				model.addAttribute("nList", nList);
				model.addAttribute("navi", result);
				return "category/notice/notice";
			} else {
				model.addAttribute("msg", "데이터가 조회되지 않습니다.");
				model.addAttribute("url", "/notice/list.do");
				return "common/serviceResult";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/notice/list.do");
			return "common/serviceResult";
		}
	}
	
	public PageInfo getPageInfo(int currentPage, int totalCount) {
		PageInfo pi = null;
		int recordCountPerPage = 5;
		int naviCountPerPage = 3;
		int naviTotalCount;
		int startNavi;
		int endNavi;
		
		naviTotalCount = (int)((double)totalCount/recordCountPerPage + 0.9);	// 0 보다 크면 +1하기위한 로직  // 소숫점을 쓰기위해 double을 사용하여 자동형변환, 그 후에 소숫점을 떨구기위해 int로 강제형변환
			// Math.ceid((double)totalCount/recordCountPerPage)
			// currentPage 값이 1~5일때 startNavi가 1로 고정되도록 구해주는 식
		startNavi = (((int)((double)currentPage/naviCountPerPage+0.9))-1) * naviCountPerPage + 1;
		endNavi = startNavi + naviCountPerPage -1;
			// endNavi는 startNavi에 무조건 naviCountPerPage 값을 더하므로
			// 실제 최대값보다 커질 수 있음
		if(endNavi > naviTotalCount) {
			endNavi = naviTotalCount;
		}
		
		pi = new PageInfo(currentPage, recordCountPerPage, naviCountPerPage, startNavi, endNavi, totalCount, naviTotalCount);
		return pi;
	}
	
	public String getNaviInfo(int currentPage, int totalCount) {
		boolean needPrev = true;		// 이전 값
		boolean needNext = true;		// 다음 값
		
		PageInfo pi = this.getPageInfo(currentPage, totalCount);
		
		if(pi.getStartNavi() == 1) {			// startNavi 가 1이면 needPrev가 뜨지않게
			needPrev = false;
		}
		if(pi.getEndNavi() == pi.getNaviTotalCount()) {	// 마지막 범위의 값이 총 범위의 갯수이면 needNext가 뜨지않게
			needNext = false;
		}
		StringBuilder result = new StringBuilder();
		if(needPrev) {
			result.append("<a href='/notice/list.do?currentPage="+(pi.getStartNavi()-1)+"'> < </a> ");
		}
		for(int i = pi.getStartNavi(); i <= pi.getEndNavi(); i++) {
			result.append("<a href='/notice/list.do?currentPage=" + i + "'>" + i + "</a>&nbsp;&nbsp;");	// 범위 만드는 html 태그  // &nbsp; 는 띄어쓰기
		}
		if(needNext) {
			result.append("<a href='/notice/list.do?currentPage="+(pi.getEndNavi()+1)+"'> > </a>");
		}
		
		return result.toString();
	}
	
	
	@RequestMapping(value="/notice/search.do", method=RequestMethod.GET)
	public String searchNoticeList(
			@RequestParam("searchCondition") String searchCondition
			, @RequestParam("searchKeyword") String searchKeyword
			, @RequestParam(value="page", required=false, defaultValue="1") Integer currentPage
			, Model model) {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("searchCondition", searchCondition);
		paramMap.put("searchKeyword", searchKeyword);
		
		int totalCount = service.getListCount(paramMap);
		PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
		
		List<Notice> searchList = service.searchNoticesByKeyword(pInfo, paramMap);		
		
		if(!searchList.isEmpty()) {
			model.addAttribute("searchCondition", searchCondition);
			model.addAttribute("searchKeyword", searchKeyword);
			model.addAttribute("pInfo", pInfo);
			model.addAttribute("sList", searchList);
			return "category/notice/notice-search";
		} else {
			model.addAttribute("msg", "데이터 조회가 완료되지 않았습니다.");
			model.addAttribute("error", "공지사항 제목으로 조회 실패");
			model.addAttribute("url", "/notice/list.do");
			return "common/serviceResult";
		}
	}
	
	
	@RequestMapping(value="/notice/detail.do", method=RequestMethod.GET)
	public String selectOneByNo(
			@RequestParam("noticeNo") int noticeNo
			, Model model) {
		
		try {
			Notice notice = service.selectOneByNo(noticeNo);
			
			if(notice != null) {
				model.addAttribute("notice", notice);
				return "category/notice/notice-detail";
			} else {
				model.addAttribute("msg", "데이터가 조회되지 않습니다.");
				model.addAttribute("url", "/notice/list.do");
				return "common/serviceResult";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/serviceResult";
		}
	}
	
	
	@RequestMapping(value="/notice/insert.do", method=RequestMethod.GET)
	public String showInsertForm() {
		return "category/notice/notice-insert";
	}
	
	@RequestMapping(value="/notice/insert.do", method=RequestMethod.POST)
	public String insertNotice(
			@RequestParam("noticeSubject") String noticeSubject
			, @RequestParam("noticeContent") String noticeContent
			, Model model) {
		
		Notice notice = new Notice(noticeSubject, noticeContent);
		
		try {
			int result = service.insertNotice(notice);
			
			if(result > 0) {
				return "redirect:/notice/list.do";
			} else {
				model.addAttribute("msg", "공지사항이 등록되지 않았습니다.");
				model.addAttribute("url", "/detail.do");
				return "common/serviceResult";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/serviceResult";
		}
	}
	
	
	@RequestMapping(value="/notice/modify.do", method=RequestMethod.GET)
	public String showUpdateForm(
			@RequestParam("noticeNo") int noticeNo
			, Model model) {
		
		try {
			Notice notice = service.selectOneByNo(noticeNo);
			model.addAttribute("notice", notice);
			return "category/notice/notice-modify";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/serviceResult";
		}
	}
	
	@RequestMapping(value="/notice/modify.do", method=RequestMethod.POST)
	public String updateNotice(
			@ModelAttribute Notice notice
//			@RequestParam("noticeNo") int noticeNo
//			, @RequestParam("noticeSubject") String noticeSubject
//			, @RequestParam("noticeContent") String noticeContent
			, Model model) {
		
//		Notice notice = new Notice(noticeNo, noticeSubject, noticeContent);
		
		
		try {
			int result = service.updateNotice(notice);
			
			if(result > 0) {
				return "redirect:/notice/list.do";
			} else {
				model.addAttribute("msg", "공지사항 수정이 완료되지 않았습니다.");
				model.addAttribute("url", "/notice/detail.do");
				return "common/serviceResult";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "/index.jsp");
			return "common/serviceResult";
		}
		
	}
	
	
	@RequestMapping(value="/notice/delete.do", method=RequestMethod.GET)
	public String deleteNoticeByNo(
			@RequestParam("noticeNo") int noticeNo
			, Model model) {
		
		try {
			int result = service.deleteNoticeByNo(noticeNo);
			
			if(result > 0) {
				return "redirect:/notice/list.do";
			} else {
				model.addAttribute("msg", "공지사항 삭제가 완료되지 않았습니다.");
				model.addAttribute("url", "/notice/detail.do");
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
