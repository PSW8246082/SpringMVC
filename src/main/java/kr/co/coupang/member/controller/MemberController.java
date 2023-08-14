package kr.co.coupang.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.coupang.member.domain.Member;
import kr.co.coupang.member.service.MemberService;


@Controller   //����
@SessionAttributes({"memberId", "memberName"})
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	//doGet
	@RequestMapping(value="/member/register.do", method=RequestMethod.GET)
	public String showRegisterForm(Model model) {
		return "member/register";
	}
	
	
	//doPost
	@RequestMapping(value="/member/register.do", method=RequestMethod.POST)
	public String registerMember(
			HttpServletRequest request
			, HttpServletResponse response
			, @RequestParam("memberId") String memberId
			, @RequestParam("memberPw") String memberPw
			, @RequestParam("memberName") String memberName
			, @RequestParam("memberAge") int memberAge
			, @RequestParam("memberGender") String memberGender
			, @RequestParam("memberEmail") String memberEmail
			, @RequestParam("memberPhone") String memberPhone
			, @RequestParam("memberAddr") String memberAddr
			, @RequestParam("memberHobby") String memberHobby
			, Model model) {
//		request.setCharacterEncoding("UTF-8");
		Member member = new Member(memberId, memberPw, memberName, memberAge, memberGender, memberEmail, memberPhone, memberAddr, memberHobby);
		try {
			int result = service.registerMember(member);
			if(result > 0) {
				//����
//				response.sendRedirect("/index.jsp");
				return  "redirect:/index.jsp";  //sendRedirect�� �����
			} else {
				//����
				model.addAttribute("msg", "ȸ������ ����");  //request.setAttribute�� �����
				return "common/errorPage";
//				request.setAttribute("mag", "����");  
//				request.getRequestDispatcher("~~~");
			}
		} catch (Exception e) {
			e.printStackTrace();  //�ܼ�â�� ���������� �߰���
			model.addAttribute("msg", e.getMessage());  //�ܼ�â�� �ߴ� �޼����� ���������� ���̰� ����
			return "common/errorPage";
		}
		
		
//		System.out.println(member.toString());
//		String memberId = request.getParameter("memberId");
//		request.setAttribute("", "");
//		request.getRequestDispatcher("/WEB-INF/views/member/register.jsp");
		

	}
	
	
	@RequestMapping(value="/member/login.do", method=RequestMethod.POST)
	public String memberLogin(
			HttpServletRequest request
			, @RequestParam("memberId") String memberId
			, @RequestParam("memberPw") String memberPw
			,Model model) {
		//SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ? AND MEMBER_PW = ?
		try {
			Member member = new Member();
			member.setMemberId(memberId);
			member.setMemberPw(memberPw);
			Member mOne = service.memberLoginCheck(member);
			if(mOne != null) {
				//�����ϸ� �α��� ������ �̵�
//				System.out.println(mOne.toString());
//				model.addAttribute("member", mOne);  //redirect�� model�� ���� ����
//				return "redirect:/index.jsp";   //void String���� �ٲ������
				//���1
//				HttpSession session = request.getSession();
//				session.setAttribute("memberId", mOne.getMemberId());
//				session.setAttribute("memberName", mOne.getMemberName());
				
				//���2
				//@SessionAttributes�� model�� �ִ� key���� Session�� �����
				model.addAttribute("memberId", mOne.getMemberId());
				model.addAttribute("memberName", mOne.getMemberName());
				//Session�� ��� ���� model�� key���� �߰����ִ� �ڵ�
				//index.jsp���� ${}�� ���� ���°Ͱ� �������
				
				return "redirect:/index.jsp";
			} else {
				//�����ϸ� ���� �޼��� ���
				model.addAttribute("msg", "�α��� ����");
				return "common/errorPage.jsp";
			}
			
		} catch (Exception e) {
			// ���� �߻��� ���� �޼��� ���
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		
	}
	}
	
	
	
	@RequestMapping(value="/member/logout.do", method=RequestMethod.GET)
	public String memberLogout(
			HttpSession sessionPrev
			//SessionStatus�� �������� ������̼�(SessionAttributes)�� �����Ǵ� ������ �����Ų��.
			//���� �޼ҵ�� setComplete(); �̴�.
			, SessionStatus session
			, Model model) {
		if(session != null) {
//			session.invalidate();
			session.setComplete();
			if(session.isComplete()) {
				//���� ���� ��ȿ�� üũ
				//�ȿ� ������ else������ �ϴϱ� isComplete() ����.
			}
			return "redirect:/index.jsp";
		} else {
			model.addAttribute("msg", "�α׾ƿ� ����");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/mypage.do", method=RequestMethod.GET)
	public String showDetailMember(
			 @RequestParam("memberId")String memberId
			 , Model model) {
		//SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ?
		try {
			Member member = service.showOneById(memberId);
			if(member != null) {
				//����
				model.addAttribute("member", member);
				return "member/mypage";
			} else {
				model.addAttribute("msg", "������ ��ȸ ����");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
		
	}
	
	
	@RequestMapping(value="/member/delete.do", method=RequestMethod.GET)   //form�±׸� post, a�±״� get���
	public String removeMember(
			@RequestParam("memberId") String memberId
			, Model model) {
		//DELETE FROM MEMBER_TBL WHERE MEMBER_ID = ?
		try {
			int result = service.removeMember(memberId);
			if(result>0) {
				//���� ������������ ���� �����ı��Ǿ���� �α׾ƿ���  SessionStatus session�� �����ϱ� /member/logout.do�� �̿��ؼ� ��
				return "redirect:/member/logout.do";
			}else {
				//����
				model.addAttribute("msg", "ȸ��Ż�����");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
		
	}
	
	
	
	
//	mypage.jsp -> modify.jsp�� ������  , void -> String���� �������ֱ�
	@RequestMapping(value="/member/update.do", method = RequestMethod.GET)
	public String modifyViewMember(
			@RequestParam("memberId")String memberId
			, Model model) {
		try {
			//SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ?
			Member member = service.showOneById(memberId);
			if(member != null) {
				model.addAttribute("member", member);  //modify.jsp���� ����� �� �ְ� ����
				return "member/modify";
			}else {
				model.addAttribute("msg", "��������ȸ ����");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
		
		
	}
	
	
	
	
	
//	modify.jsp -> mypage.jsp�� �̵�
	@RequestMapping(value="/member/update.do", method = RequestMethod.POST)
	public String modifyMember(
			@RequestParam("memberId") String memberId
			, @RequestParam("memberPw") String memberPw
			, @RequestParam("memberEmail") String  memberEmail
			, @RequestParam("memberPhone") String  memberPhone
			, @RequestParam("memberAddr") String  memberAddr
			, @RequestParam("memberHobby") String  memberHobby
			, RedirectAttributes redirect
			, Model model) {
		//UPDATE MEMBER_TBL SET MEMBER_PW = ?, MEMBER_EMAIL = ?, MEMBER_PHONE = ?, MEMBER_ADDRESS = ?, MEMBER_HOBBY = ? WHERE MEMBER_ID = ?
		try {
			Member member = new Member(memberId, memberPw, memberEmail, memberPhone, memberAddr, memberHobby);
			int result = service.updateMember(member);
			if(result>0) {
				//���� -> ������������ �̵�
//				response.sendRedirect("/member/detail.do?memberId=" + memberId);  //$�� JSP������ ���°�
				redirect.addAttribute("memberId", memberId);  //redirect�� ������Ʈ�� �����ִ� ��
				return "redirect:/member/mypage.do";  //$�� JSP������ ���°�  , ?�� �������� +��� ���� �־���
			} else {
				//���� -> ���������� �̵�
				model.addAttribute("msg", "�������� �Ϸ� ����");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}

		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
