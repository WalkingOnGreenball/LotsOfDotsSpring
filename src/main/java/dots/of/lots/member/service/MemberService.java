package dots.of.lots.member.service;

import dots.of.lots.member.domain.Member;

public interface MemberService {
	
	/**
	 * 회원 등록
	 * @param member
	 * @return int
	 */
	public int insertMember(Member member);

	/**
	 * 회원 수정
	 * @param member
	 * @return int
	 */
	public int updateMember(Member member);

	/**
	 * 회원 삭제
	 * @param memberId
	 * @return int
	 */
	public int deleteMember(String memberId);

	/**
	 * 회원 로그인
	 * @param memberId, memberPw
	 * @return member
	 */
	public Member selectCheckLogin(Member member);

	/**
	 * 회원 아이디로 조회
	 * @param memberId
	 * @return member
	 */
	public Member selectOneById(String memberId);

	/**
	 * 회원 아이디 찾기
	 * @param memberEmail, memberPhone
	 * @return member
	 */
	public Member findId(Member member);

	/**
	 * 회원 비밀번호 찾기
	 * @param memberId, memberPhone
	 * @return member
	 */
	public Member findPw(Member member);

}
