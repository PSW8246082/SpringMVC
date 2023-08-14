package kr.co.coupang.member.service;

import kr.co.coupang.member.domain.Member;

public interface MemberService {
	
	/**
	 * 멤버등록 Service
	 * @param member
	 * @return int
	 */
	public int registerMember(Member member);

	
	/**
	 * 멤버로그인 Service
	 * @param 아이디, 비번
	 * @return member객체
	 */
	public Member memberLoginCheck(Member member);
	
	
	/**
	 * 회원상세조회
	 * @param memberId
	 * @return
	 */
	public Member showOneById(String memberId);


	/**
	 * 회원탈퇴(삭제)
	 * @param memberId
	 * @return
	 */
	public int removeMember(String memberId);


	/**
	 * 회원정보수정
	 * @param member
	 * @return
	 */
	public int updateMember(Member member);
	
	
	
	
}
