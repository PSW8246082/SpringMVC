package kr.co.coupang.member.domain;

public class Member {
	private String memberId;
	private String memberPw;
	private String memberName;
	private int memberAge;
	private String memberGender;
	private String memberEmail;
	private String memberPhone;
	private String memberAddr;
	private String memberHobby;
	private String memberDate;
	private String updateDate;
	private String memberYn;
	
	public Member() {
		// TODO Auto-generated constructor stub
	}
	
	
	

	public Member(String memberId, String memberPw, String memberEmail, String memberPhone, String memberAddr,
			String memberHobby) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberEmail = memberEmail;
		this.memberPhone = memberPhone;
		this.memberAddr = memberAddr;
		this.memberHobby = memberHobby;
	}




	public Member(String memberId, String memberPw, String memberName, String memberEmail, String memberPhone,
			String memberAddr, String memberHobby) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.memberEmail = memberEmail;
		this.memberPhone = memberPhone;
		this.memberAddr = memberAddr;
		this.memberHobby = memberHobby;
	}




	public Member(String memberId, String memberPw, String memberName, int memberAge, String memberGender,
			String memberEmail, String memberPhone, String memberAddr, String memberHobby) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.memberAge = memberAge;
		this.memberGender = memberGender;
		this.memberEmail = memberEmail;
		this.memberPhone = memberPhone;
		this.memberAddr = memberAddr;
		this.memberHobby = memberHobby;
	}




	public String getMemberId() {
		return memberId;
	}




	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}




	public String getMemberPw() {
		return memberPw;
	}




	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}




	public String getMemberName() {
		return memberName;
	}




	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}




	public int getMemberAge() {
		return memberAge;
	}




	public void setMemberAge(int memberAge) {
		this.memberAge = memberAge;
	}




	public String getMemberGender() {
		return memberGender;
	}




	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}




	public String getMemberEmail() {
		return memberEmail;
	}




	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}




	public String getMemberPhone() {
		return memberPhone;
	}




	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}




	public String getMemberAddr() {
		return memberAddr;
	}




	public void setMemberAddr(String memberAddr) {
		this.memberAddr = memberAddr;
	}




	public String getMemberHobby() {
		return memberHobby;
	}




	public void setMemberHobby(String memberHobby) {
		this.memberHobby = memberHobby;
	}




	public String getMemberDate() {
		return memberDate;
	}




	public void setMemberDate(String memberDate) {
		this.memberDate = memberDate;
	}




	public String getUpdateDate() {
		return updateDate;
	}




	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}




	public String getMemberYn() {
		return memberYn;
	}




	public void setMemberYn(String memberYn) {
		this.memberYn = memberYn;
	}




	@Override
	public String toString() {
		return "ȸ�� [���̵�=" + memberId + ", ��й�ȣ=" + memberPw + ", �̸�=" + memberName + ", ����="
				+ memberAge + ", ����=" + memberGender + ", �̸���=" + memberEmail + ",  ��ȭ��ȣ="
				+ memberPhone + ", �ּ�=" + memberAddr + ", ���=" + memberHobby + ", ���Գ�¥="
				+ memberDate + ", ��������=" + updateDate + ", ȸ������=" + memberYn + "]";
	}







	
	
	
}
