package dots.of.lots.member.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dots.of.lots.member.domain.Member;
import dots.of.lots.member.service.MemberService;
import dots.of.lots.member.store.MemberStore;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private MemberStore mStore;

	@Override
	public int insertMember(Member member) {
		int result = mStore.insertMember(sqlSession, member);
		return result;
	}

	@Override
	public int updateMember(Member member) {
		int result = mStore.updateMember(sqlSession, member);
		return result;
	}

	@Override
	public int deleteMember(String memberId) {
		int result = mStore.deleteMember(sqlSession, memberId);
		return result;
	}

	@Override
	public Member selectCheckLogin(Member member) {
		Member mOne = mStore.selectCheckLogin(sqlSession, member);
		return mOne;
	}

	@Override
	public Member selectOneById(String memberId) {
		Member member = mStore.selectOneById(sqlSession, memberId);
		return member;
	}

	@Override
	public Member findId(Member member) {
		Member mOne = mStore.findId(sqlSession, member);
		return mOne;
	}

	@Override
	public Member findPw(Member member) {
		Member mOne = mStore.findPw(sqlSession, member);
		return mOne;
	}

}
