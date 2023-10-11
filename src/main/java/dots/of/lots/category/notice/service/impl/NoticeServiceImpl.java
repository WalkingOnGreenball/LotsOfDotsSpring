package dots.of.lots.category.notice.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dots.of.lots.category.notice.domain.Notice;
import dots.of.lots.category.notice.domain.PageInfo;
import dots.of.lots.category.notice.service.NoticeService;
import dots.of.lots.category.notice.store.NoticeStore;

@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private NoticeStore nStore;
	
	@Override
	public int insertNotice(Notice notice) {
		int result = nStore.insertNotice(sqlSession, notice);
		return result;
	}

	@Override
	public int updateNotice(Notice notice) {
		int result = nStore.updateNotice(sqlSession, notice);
		return result;
	}

	@Override
	public int deleteNoticeByNo(int noticeNo) {
		int result = nStore.deleteNoticeByNo(sqlSession, noticeNo);
		return result;
	}

	@Override
	public int deleteNotice(Notice notice) {
		int result = nStore.deleteNotice(sqlSession, notice);
		return result;
	}

	@Override
	public Notice selectOneByNo(int noticeNo) {
		Notice notice = nStore.selectOneByNo(sqlSession, noticeNo);
		return notice;
	}

	@Override
	public int getListCount() {
		int result = nStore.getListCount(sqlSession);
		return result;
	}

	@Override
	public List<Notice> selectNoticeList(PageInfo pInfo) {
		List<Notice> nList = nStore.selectNoticeList(sqlSession, pInfo);
		return nList;
	}

	@Override
	public int getListCount(Map<String, String> paramMap) {
		int result = nStore.getListCount(sqlSession, paramMap);
		return result;
	}

	@Override
	public List<Notice> searchNoticesByKeyword(PageInfo pInfo, Map<String, String> paramMap) {
		List<Notice> searchList = nStore.searchNoticesByKeyword(sqlSession, pInfo, paramMap);
		return searchList;
	}
	
	

}
