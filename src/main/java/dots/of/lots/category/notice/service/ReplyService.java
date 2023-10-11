package dots.of.lots.category.notice.service;

import java.util.List;

import dots.of.lots.category.notice.domain.Reply;

public interface ReplyService {

	/**
	 * 댓글 전체 조회
	 * @param noticeNo
	 * @return
	 */
	public List<Reply> selectReplyList(int refBoardNo);

	/**
	 * 댓글 전체 조회
	 * @return
	 */
	public int insertReply(Reply reply);

	/**
	 * 댓글 수정
	 * @param reply
	 * @return
	 */
	public int updateReply(Reply reply);

	/**
	 * 댓글 삭제
	 * @param reply
	 * @return
	 */
	public int deleteReply(Reply reply);

	
}
