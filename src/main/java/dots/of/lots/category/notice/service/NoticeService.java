package dots.of.lots.category.notice.service;

import java.util.List;
import java.util.Map;

import dots.of.lots.category.notice.domain.Notice;
import dots.of.lots.category.notice.domain.PageInfo;

public interface NoticeService {

	/**
	 * 공지 등록
	 * @param notice
	 * @return int
	 */
	public int insertNotice(Notice notice);

	/**
	 * 공지 수정
	 * @param notice
	 * @return int
	 */
	public int updateNotice(Notice notice);

	/**
	 * 공지 삭제
	 * @param noticeNo
	 * @return
	 */
	public int deleteNoticeByNo(int noticeNo);

	/**
	 * 공지 번호로 조회
	 * @param noticeNo
	 * @return Notice
	 */
	public Notice selectOneByNo(int noticeNo);

	/**
	 * 공지사항 전체 갯수 조회
	 * @return
	 */
	public int getListCount();

	/**
	 * 리스트 조회
	 * @return list
	 */
	public List<Notice> selectNoticeList(PageInfo pInfo);

	/**
	 * 공지사항 조건에 따라 키워드로 갯수 조회
	 * @param paramMap
	 * @return
	 */
	public int getListCount(Map<String, String> paramMap);

	/**
	 * 공지사항 조건에 따라 키워드로 검색
	 * @param searchKeyword
	 * @return
	 */
	public List<Notice> searchNoticesByKeyword(PageInfo pInfo, Map<String, String> paramMap);

}
