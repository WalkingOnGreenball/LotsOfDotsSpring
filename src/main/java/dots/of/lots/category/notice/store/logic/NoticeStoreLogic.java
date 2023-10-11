package dots.of.lots.category.notice.store.logic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import dots.of.lots.category.notice.domain.Notice;
import dots.of.lots.category.notice.domain.PageInfo;
import dots.of.lots.category.notice.store.NoticeStore;

@Repository
public class NoticeStoreLogic implements NoticeStore{

	@Override
	public int insertNotice(SqlSession sqlSession, Notice notice) {
		int result = sqlSession.insert("NoticeMapper.insertNotice", notice);
		return result;
	}

	@Override
	public int updateNotice(SqlSession sqlSession, Notice notice) {
		int result = sqlSession.insert("NoticeMapper.updateNotice", notice);
		return result;
	}

	@Override
	public int deleteNoticeByNo(SqlSession sqlSession, int noticeNo) {
		int result = sqlSession.delete("NoticeMapper.deleteNoticeByNo", noticeNo);
		return result;
	}

	@Override
	public int deleteNotice(SqlSession sqlSession, Notice notice) {
		int result = sqlSession.update("NoticeMapper.deleteNotice", notice);
		return result;
	}
	
//	@Override
//	public int deleteNotice(SqlSession sqlSession, Notice notice) {
//		int result = sqlSession.delete("NoticeMapper.deleteNotice", notice);
//		return result;
//	}

	@Override
	public Notice selectOneByNo(SqlSession sqlSession, int noticeNo) {
		Notice notice = sqlSession.selectOne("NoticeMapper.selectOneByNo", noticeNo);
		return notice;
	}

	@Override
	public int getListCount(SqlSession sqlSession) {
		int result = sqlSession.selectOne("NoticeMapper.selectNoticeCount");
		return result;
	}

	@Override
	public List<Notice> selectNoticeList(SqlSession sqlSession, PageInfo pInfo) {
		int limit = pInfo.getRecordCountPerPage();
		int offset = (pInfo.getCurrentPage()-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Notice> nList = sqlSession.selectList("NoticeMapper.selectNoticeList", null, rowBounds);
		return nList;
	}

	@Override
	public int getListCount(SqlSession sqlSession, Map<String, String> paramMap) {
		int result = sqlSession.selectOne("NoticeMapper.selectNoticeCountByKeyword", paramMap);
		return result;
	}

	@Override
	public List<Notice> searchNoticesByKeyword(SqlSession sqlSession, PageInfo pInfo, Map<String, String> paramMap) {
		int limit = pInfo.getRecordCountPerPage();
		int offset = (pInfo.getCurrentPage()-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Notice> searchList = sqlSession.selectList("NoticeMapper.searchNoticesByKeyword", paramMap, rowBounds);
		return searchList;
	}

}
