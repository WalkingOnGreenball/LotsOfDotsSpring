package dots.of.lots.category.notice.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dots.of.lots.category.notice.domain.Reply;
import dots.of.lots.category.notice.service.ReplyService;
import dots.of.lots.category.notice.store.ReplyStore;

@Repository
public class ReplyServiceImpl implements ReplyService{

	@Autowired
	private ReplyStore rStore;
	@Autowired
	private SqlSession session;
	
	@Override
	public List<Reply> selectReplyList(int refBoardNo) {
		List<Reply> rList = rStore.selectReplyList(session, refBoardNo);
		return rList;
	}
	@Override
	public int insertReply(Reply reply) {
		int result = rStore.insertReply(session, reply);
		return result;
	}
	@Override
	public int updateReply(Reply reply) {
		int result = rStore.updateReply(session, reply);
		return result;
	}
	@Override
	public int deleteReply(Reply reply) {
		int result = rStore.deleteReply(session, reply);
		return result;
	}
	
	
	
}
