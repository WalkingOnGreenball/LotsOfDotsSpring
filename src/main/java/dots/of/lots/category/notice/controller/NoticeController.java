package dots.of.lots.category.notice.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dots.of.lots.category.notice.domain.Notice;
import dots.of.lots.category.notice.domain.PageInfo;
import dots.of.lots.category.notice.domain.Reply;
import dots.of.lots.category.notice.service.NoticeService;
import dots.of.lots.category.notice.service.ReplyService;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService nService;
	@Autowired
	private ReplyService rService;
	
	@RequestMapping(value="/notice/list.do", method=RequestMethod.GET)
	public ModelAndView selectNoticeList(ModelAndView mv
			, @RequestParam(value="currentPage", required=false, defaultValue="1") Integer currentPage) {
		
		try {
			int totalCount = nService.getListCount();
			PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
			String result = this.getNaviInfo(currentPage, totalCount);
			
			List<Notice> nList = nService.selectNoticeList(pInfo);
			
			if(!nList.isEmpty()) {
				mv.addObject("pInfo", pInfo);
				mv.addObject("nList", nList);
				mv.addObject("navi", result);
				mv.setViewName("category/notice/notice");
			} else {
				mv.addObject("msg", "데이터가 조회되지 않습니다.");
				mv.addObject("url", "/notice/list.do");
				mv.setViewName("common/serviceResult");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("url", "/notice/list.do");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	public PageInfo getPageInfo(int currentPage, int totalCount) {
		PageInfo pInfo = null;
		int recordCountPerPage = 5;
		int naviCountPerPage = 3;
		int naviTotalCount;
		int startNavi;
		int endNavi;
		
		naviTotalCount = (int)((double)totalCount/recordCountPerPage + 0.9);
		startNavi = (((int)((double)currentPage/naviCountPerPage+0.9))-1) * naviCountPerPage + 1;
		endNavi = startNavi + naviCountPerPage -1;
		if(endNavi > naviTotalCount) {
			endNavi = naviTotalCount;
		}
		
		pInfo = new PageInfo(currentPage, recordCountPerPage, naviCountPerPage, startNavi, endNavi, totalCount, naviTotalCount);
		return pInfo;
	}
	
	public String getNaviInfo(int currentPage, int totalCount) {
		boolean needPrev = true;
		boolean needNext = true;
		
		PageInfo pi = this.getPageInfo(currentPage, totalCount);
		
		if(pi.getStartNavi() == 1) {
			needPrev = false;
		}
		if(pi.getEndNavi() == pi.getNaviTotalCount()) {
			needNext = false;
		}
		StringBuilder result = new StringBuilder();
		if(needPrev) {
			result.append("<a href='/notice/list.do?currentPage="+(pi.getStartNavi()-1)+"'> < </a> ");
		}
		for(int i = pi.getStartNavi(); i <= pi.getEndNavi(); i++) {
			result.append("<a href='/notice/list.do?currentPage=" + i + "'>" + i + "</a>");
		}
		if(needNext) {
			result.append("<a href='/notice/list.do?currentPage="+(pi.getEndNavi()+1)+"'> > </a>");
		}
		
		return result.toString();
	}
	
	@RequestMapping(value="/notice/search.do", method=RequestMethod.GET)
	public ModelAndView searchNoticeList(ModelAndView mv
			, @RequestParam("searchCondition") String searchCondition
			, @RequestParam("searchKeyword") String searchKeyword
			, @RequestParam(value="currentPage", required=false, defaultValue="1") Integer currentPage) {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("searchCondition", searchCondition);
		paramMap.put("searchKeyword", searchKeyword);
		
		int totalCount = nService.getListCount(paramMap);
		PageInfo pInfo = this.getPageInfo(currentPage, totalCount);
		String result = this.getSearchNaviInfo(pInfo, paramMap);
		
		List<Notice> searchList = nService.searchNoticesByKeyword(pInfo, paramMap);		
		
		if(!searchList.isEmpty()) {
			mv.addObject("searchCondition", searchCondition);
			mv.addObject("searchKeyword", searchKeyword);
			mv.addObject("pInfo", pInfo);
			mv.addObject("sList", searchList);
			mv.addObject("navi", result);
			mv.setViewName("category/notice/notice-search");
		} else {
			mv.addObject("msg", "데이터 조회가 완료되지 않았습니다.");
			mv.addObject("url", "/notice/list.do");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	public String getSearchNaviInfo(PageInfo pInfo, Map<String, String> paramMap) {
		boolean needPrev = true;
		boolean needNext = true;
		
		if(pInfo.getStartNavi() == 1) {
			needPrev = false;
		}
		if(pInfo.getEndNavi() == pInfo.getNaviTotalCount()) {
			needNext = false;
		}
		StringBuilder result = new StringBuilder();
		if(needPrev) {
			result.append("<a href='/notice/search.do?currentPage="+(pInfo.getStartNavi()-1)+"&searchCondition="+paramMap.get("searchCondition")+"&searchKeyword="+paramMap.get("searchKeyword")+"'> < </a>");
		}
		for(int i = pInfo.getStartNavi(); i <= pInfo.getEndNavi(); i++) {
//			result.append("<a href='/notice/search.do?currentPage=" + i + "'>" + i + "</a>");
			result.append("<a href='/notice/search.do?currentPage="+i+"&searchCondition="+paramMap.get("searchCondition")+"&searchKeyword="+paramMap.get("searchKeyword")+"'>" +i+ "</a>");
		}
		if(needNext) {
			result.append("<a href='/notice/search.do?currentPage="+(pInfo.getEndNavi()+1)+"&searchCondition="+paramMap.get("searchCondition")+"&searchKeyword="+paramMap.get("searchKeyword")+"'> > </a>");
		}
		
		return result.toString();
	}
	
	@RequestMapping(value="/notice/detail.do", method=RequestMethod.GET)
	public ModelAndView showNoticeDetail(ModelAndView mv
			, @RequestParam("noticeNo") Integer noticeNo) {
		
		try {
			Notice notice = nService.selectOneByNo(noticeNo);
			
			if(notice != null) {
				List<Reply> replyList = rService.selectReplyList(noticeNo);
				if(replyList.size() > 0) {
					mv.addObject("rList", replyList);
				}
				mv.addObject("notice", notice);
				mv.setViewName("category/notice/notice-detail");
			} else {
				mv.addObject("msg", "데이터가 조회되지 않습니다.");
				mv.addObject("url", "/notice/list.do");
				mv.setViewName("common/serviceResult");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("url", "/notice/list.do");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	@RequestMapping(value="/notice/insert.do", method=RequestMethod.GET)
	public ModelAndView showInsertForm(ModelAndView mv
			, HttpSession session) {
		
		try {
			String noticeWriter = (String)session.getAttribute("memberId");
			if(noticeWriter != null) {
				mv.setViewName("category/notice/notice-insert");
			} else {
				mv.addObject("msg", "게시글 등록 권한이 없습니다.");
				mv.addObject("url", "/member/login.do");
				mv.setViewName("common/serviceResult");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("url", "/notice/list.do");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	@RequestMapping(value="/notice/insert.do", method=RequestMethod.POST)
	public ModelAndView insertNotice(ModelAndView mv
			, @ModelAttribute Notice notice
			, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			, HttpServletRequest request
			, HttpSession session) {
		
		try {
			String noticeWriter = (String)session.getAttribute("memberId");
			if(noticeWriter != null) {
				notice.setNoticeWriter(noticeWriter);
				
				if(uploadFile != null && !uploadFile.getOriginalFilename().equals("")) {
					Map<String, Object> infoMap = this.saveFile(uploadFile, request);
					String noticeFileName = (String)infoMap.get("fileName");
					String noticeFileRename = (String)infoMap.get("fileRename");
					String noticeFilePath = (String)infoMap.get("filePath");
					long noticeFileLength = (long)infoMap.get("fileLength");
					
					notice.setNoticeFileName(noticeFileName);
					notice.setNoticeFileRename(noticeFileRename);
					notice.setNoticeFilePath(noticeFilePath);
					notice.setNoticeFileLength(noticeFileLength);
				}
				int result = nService.insertNotice(notice);
					
				if(result > 0) {
					mv.setViewName("redirect:/notice/list.do");
				} else {
					mv.addObject("msg", "공지사항이 등록되지 않았습니다.");
					mv.addObject("url", "/detail.do");
					mv.setViewName("common/serviceResult");
				}
			} else {
				mv.addObject("msg", "게시글 등록 권한이 없습니다.");
				mv.addObject("url", "/member/login.do");
				mv.setViewName("common/serviceResult");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의해주세요.");
			mv.addObject("url", "/notice/list.do");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	public Map<String, Object> saveFile(MultipartFile uploadFile, HttpServletRequest request) throws Exception {
		Map<String, Object> infoMap = new HashMap<String, Object>();
		
		String fileName = uploadFile.getOriginalFilename();
		String root = request.getSession().getServletContext().getRealPath("resources");
		String saveFolder = root + "//nuploadFiles";
		File folder = new File(saveFolder);
		if(!folder.exists()) {
			folder.mkdir();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String strResult = sdf.format(new Date(System.currentTimeMillis()));
		
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		String fileRename = "N"+strResult+"."+ext;
		String savePath = saveFolder + "//" + fileName;
		File file = new File(savePath);
		uploadFile.transferTo(file);
		long fileLength = uploadFile.getSize();
	
		infoMap.put("fileName", fileName);
		infoMap.put("fileRename", fileRename);
		infoMap.put("filePath", savePath);
		infoMap.put("fileLength", fileLength);
		
		return infoMap;
	}
	
	@RequestMapping(value="/notice/modify.do", method = RequestMethod.GET)
	public ModelAndView showUpdateForm(ModelAndView mv
			, @RequestParam("noticeNo") Integer noticeNo) {
		
		try {
			Notice notice = nService.selectOneByNo(noticeNo);
			mv.addObject("notice", notice);
			mv.setViewName("category/notice/notice-modify");
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의하세요.");
			mv.addObject("url", "/index.jsp");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	@RequestMapping(value="/notice/modify.do", method=RequestMethod.POST)
	public ModelAndView updateNotice(ModelAndView mv
			, @ModelAttribute Notice notice
			, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile
			, HttpServletRequest request
			, HttpSession session) {
		
		String url = "/notice/detail.do?noticeNo="+notice.getNoticeNo();
		try {
			String memberId = (String)session.getAttribute("memberId");
			String noticeWriter = notice.getNoticeWriter();
			if(noticeWriter != null & noticeWriter.equals(memberId)) {
				if(uploadFile != null && !uploadFile.getOriginalFilename().equals("")) {
					String fileName = notice.getNoticeFileRename();
					if(fileName != null) {
						this.deleteFile(request, fileName);
					}
					Map<String, Object> infoMap = this.saveFile(uploadFile, request);
					String noticeFileName = (String)infoMap.get("fileName");
					String noticeFileRename = (String)infoMap.get("fileRename");
					String noticeFilePath = (String)infoMap.get("filePath");
					long noticeFileLength = (long)infoMap.get("fileLength");
					
					notice.setNoticeFileName(noticeFileName);
					notice.setNoticeFileRename(noticeFileRename);
					notice.setNoticeFilePath(noticeFilePath);
					notice.setNoticeFileLength(noticeFileLength);
				}
				int result = nService.updateNotice(notice);
				
				if(result > 0) {
					mv.setViewName("redirect:"+url);
				} else {
					mv.addObject("msg", "게시글 수정이 완료되지 않았습니다.");
					mv.addObject("url", url);
					mv.setViewName("common/serviceResult");
				}
			} else {
				mv.addObject("msg", "자신의 게시글만 삭제 가능합니다.");
				mv.addObject("url", url);
				mv.setViewName("common/serviceResult");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "관리자에게 문의하세요.");
			mv.addObject("url", url);
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
	private void deleteFile(HttpServletRequest request, String fileName) {
		String root = request.getSession().getServletContext().getRealPath("resources");	// 경로 가져오기
		String delFilePath = root+"/nuploadFiles/"+fileName;
		File file = new File(delFilePath);
		if(file.exists()) {
			file.delete();
		}
	}
	
	@RequestMapping(value="/notice/delete.do", method = RequestMethod.GET)
	public ModelAndView deleteNotice(ModelAndView mv
			, @ModelAttribute Notice notice
			, HttpSession session) {
		try {
			String memberId = (String)session.getAttribute("memberId");
			String noticeWriter = notice.getNoticeWriter();
			if(noticeWriter != null && noticeWriter.equals(memberId)) {	// 세션 아이디, 작성자 비교
				int result = nService.deleteNotice(notice);
				if(result > 0) {
					mv.setViewName("redirect:/notice/list.do");
				} else {
					mv.addObject("msg", "댓글삭제가 완료되지 않았습니다.");
					mv.addObject("url", "/notice/detail.do");
					mv.setViewName("common/serviceResult");
				}
			} else {
				mv.addObject("msg", "자신의 댓글만 삭제 가능합니다.");
				mv.addObject("error", "댓글 삭제 불가");
				mv.addObject("url", "/notice/list.do");
				mv.setViewName("common/errorPage");
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", e.getMessage());
			mv.addObject("url", "/index.jsp");
			mv.setViewName("common/serviceResult");
		}
		return mv;
	}
	
}
