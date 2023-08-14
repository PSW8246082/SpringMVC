package kr.co.coupang.member.service;

import kr.co.coupang.member.domain.Member;

public interface MemberService {
	
	/**
	 * ������ Service
	 * @param member
	 * @return int
	 */
	public int registerMember(Member member);

	
	/**
	 * ����α��� Service
	 * @param ���̵�, ���
	 * @return member��ü
	 */
	public Member memberLoginCheck(Member member);
	
	
	/**
	 * ȸ������ȸ
	 * @param memberId
	 * @return
	 */
	public Member showOneById(String memberId);


	/**
	 * ȸ��Ż��(����)
	 * @param memberId
	 * @return
	 */
	public int removeMember(String memberId);


	/**
	 * ȸ����������
	 * @param member
	 * @return
	 */
	public int updateMember(Member member);
	
	
	
	
}
