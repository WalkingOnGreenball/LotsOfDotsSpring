package dots.of.lots.member.store;

import org.apache.ibatis.session.SqlSession;

import dots.of.lots.member.domain.Member;

public interface MemberStore {

	public int insertMember(SqlSession sqlSession, Member member);

	public int updateMember(SqlSession sqlSession, Member member);

	public int deleteMember(SqlSession sqlSession, String memberId);

	public Member selectCheckLogin(SqlSession sqlSession, Member member);

	public Member selectOneById(SqlSession sqlSession, String memberId);

	public Member findId(SqlSession sqlSession, Member member);

	public Member findPw(SqlSession sqlSession, Member member);

}
