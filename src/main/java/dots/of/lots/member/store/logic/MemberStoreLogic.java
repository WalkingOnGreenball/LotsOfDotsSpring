package dots.of.lots.member.store.logic;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import dots.of.lots.member.domain.Member;
import dots.of.lots.member.store.MemberStore;

@Repository
public class MemberStoreLogic implements MemberStore{

	@Override
	public int insertMember(SqlSession sqlSession, Member member) {
		int result = sqlSession.insert("MemberMapper.insertMember", member);
		return result;
	}

	@Override
	public int updateMember(SqlSession sqlSession, Member member) {
		int result = sqlSession.update("MemberMapper.updateMember", member);
		return result;
	}

	@Override
	public int deleteMember(SqlSession sqlSession, String memberId) {
		int result = sqlSession.delete("MemberMapper.deleteMember", memberId);
		return result;
	}

	@Override
	public Member selectCheckLogin(SqlSession sqlSession, Member member) {
		Member mOne = sqlSession.selectOne("MemberMapper.selectCheckLogin", member);
		return mOne;
	}

	@Override
	public Member selectOneById(SqlSession sqlSession, String memberId) {
		Member member = sqlSession.selectOne("MemberMapper.selectOneById", memberId);
		return member;
	}

	@Override
	public Member findId(SqlSession sqlSession, Member member) {
		Member mOne = sqlSession.selectOne("MemberMapper.findId", member);
		return mOne;
	}

	@Override
	public Member findPw(SqlSession sqlSession, Member member) {
		Member mOne = sqlSession.selectOne("MemberMapper.findPw", member);
		return mOne;
	}

}
