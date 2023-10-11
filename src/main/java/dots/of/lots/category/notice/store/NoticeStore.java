package dots.of.lots.category.notice.store;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import dots.of.lots.category.notice.domain.Notice;
import dots.of.lots.category.notice.domain.PageInfo;

public interface NoticeStore {

	public int insertNotice(SqlSession sqlSession, Notice notice);

	public int updateNotice(SqlSession sqlSession, Notice notice);

	public int deleteNoticeByNo(SqlSession sqlSession, int noticeNo);

	public int deleteNotice(SqlSession sqlSession, Notice notice);

	public Notice selectOneByNo(SqlSession sqlSession, int noticeNo);

	public int getListCount(SqlSession sqlSession);

	public List<Notice> selectNoticeList(SqlSession sqlSession, PageInfo pInfo);

	public int getListCount(SqlSession sqlSession, Map<String, String> paramMap);

	public List<Notice> searchNoticesByKeyword(SqlSession sqlSession, PageInfo pInfo, Map<String, String> paramMap);

}
