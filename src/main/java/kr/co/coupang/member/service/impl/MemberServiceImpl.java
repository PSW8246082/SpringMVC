package kr.co.coupang.member.service.impl;

import java.sql.Connection;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.coupang.member.domain.Member;
import kr.co.coupang.member.service.MemberService;
import kr.co.coupang.member.store.MemberStore;

@Service  //빈등록
public class MemberServiceImpl implements MemberService{
	
	@Autowired  //의존성 주입  connection을 대체함
	private SqlSession sqlSession;  //root-context.xml과 관련있음
	@Autowired
	private MemberStore mStore;
	
	
	@Override
	public int registerMember(Member member) {
//		Connection conn = jdbcTemplate.createConnection();
		int result = mStore.insertMember(sqlSession, member);
		return result;
	}


	@Override
	public Member memberLoginCheck(Member member) {
		Member mOne = mStore.selectMemberLogin(sqlSession, member);
		return mOne;
	}


	@Override
	public Member showOneById(String memberId) {
		Member mOne = mStore.selectOneById(sqlSession, memberId);
		return mOne;
	}


	@Override
	public int removeMember(String memberId) {
		int result = mStore.deleteMember(sqlSession, memberId);
		return result;
	}


	@Override
	public int updateMember(Member member) {
		int result = mStore.updateMember(sqlSession, member);
		return result;
	}


	
}
