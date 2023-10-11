package dots.of.lots.category.notice.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import dots.of.lots.category.notice.domain.Reply;

public interface ReplyStore {

	public List<Reply> selectReplyList(SqlSession session, int refBoardNo);

	public int insertReply(SqlSession session, Reply reply);

	public int updateReply(SqlSession session, Reply reply);

	public int deleteReply(SqlSession session, Reply reply);

	
}
