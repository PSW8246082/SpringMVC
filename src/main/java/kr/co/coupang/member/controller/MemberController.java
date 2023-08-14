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


@Controller   //빈등록
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
				//성공
//				response.sendRedirect("/index.jsp");
				return  "redirect:/index.jsp";  //sendRedirect를 대신함
			} else {
				//실패
				model.addAttribute("msg", "회원가입 실패");  //request.setAttribute를 대신함
				return "common/errorPage";
//				request.setAttribute("mag", "실패");  
//				request.getRequestDispatcher("~~~");
			}
		} catch (Exception e) {
			e.printStackTrace();  //콘솔창에 빨간색으로 뜨게함
			model.addAttribute("msg", e.getMessage());  //콘솔창에 뜨는 메세지를 웹페이지에 보이게 해줌
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
				//성공하면 로그인 페이지 이동
//				System.out.println(mOne.toString());
//				model.addAttribute("member", mOne);  //redirect랑 model은 같이 못씀
//				return "redirect:/index.jsp";   //void String으로 바꿔줘야함
				//방법1
//				HttpSession session = request.getSession();
//				session.setAttribute("memberId", mOne.getMemberId());
//				session.setAttribute("memberName", mOne.getMemberName());
				
				//방법2
				//@SessionAttributes는 model에 있는 key값을 Session에 담아줌
				model.addAttribute("memberId", mOne.getMemberId());
				model.addAttribute("memberName", mOne.getMemberName());
				//Session에 담기 위해 model에 key값을 추가해주는 코드
				//index.jsp에서 ${}를 통해 쓰는것과 상관없음
				
				return "redirect:/index.jsp";
			} else {
				//실패하면 실패 메세지 출력
				model.addAttribute("msg", "로그인 실패");
				return "common/errorPage.jsp";
			}
			
		} catch (Exception e) {
			// 예외 발생시 예외 메세지 출력
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		
	}
	}
	
	
	
	@RequestMapping(value="/member/logout.do", method=RequestMethod.GET)
	public String memberLogout(
			HttpSession sessionPrev
			//SessionStatus는 스프링의 어노테이션(SessionAttributes)로 지원되는 세션을 만료시킨다.
			//사용된 메소드는 setComplete(); 이다.
			, SessionStatus session
			, Model model) {
		if(session != null) {
//			session.invalidate();
			session.setComplete();
			if(session.isComplete()) {
				//세션 만료 유효성 체크
				//안에 넣으면 else만들어야 하니까 isComplete() 참고만.
			}
			return "redirect:/index.jsp";
		} else {
			model.addAttribute("msg", "로그아웃 실패");
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
				//성공
				model.addAttribute("member", member);
				return "member/mypage";
			} else {
				model.addAttribute("msg", "데이터 조회 실패");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
		
	}
	
	
	@RequestMapping(value="/member/delete.do", method=RequestMethod.GET)   //form태그만 post, a태그는 get방식
	public String removeMember(
			@RequestParam("memberId") String memberId
			, Model model) {
		//DELETE FROM MEMBER_TBL WHERE MEMBER_ID = ?
		try {
			int result = service.removeMember(memberId);
			if(result>0) {
				//성공 메인페이지로 가고 세션파괴되어야함 로그아웃에  SessionStatus session이 있으니까 /member/logout.do을 이용해서 씀
				return "redirect:/member/logout.do";
			}else {
				//실패
				model.addAttribute("msg", "회원탈퇴실패");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
		
	}
	
	
	
	
//	mypage.jsp -> modify.jsp로 가야함  , void -> String으로 수정해주기
	@RequestMapping(value="/member/update.do", method = RequestMethod.GET)
	public String modifyViewMember(
			@RequestParam("memberId")String memberId
			, Model model) {
		try {
			//SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ?
			Member member = service.showOneById(memberId);
			if(member != null) {
				model.addAttribute("member", member);  //modify.jsp에서 사용할 수 있게 해줌
				return "member/modify";
			}else {
				model.addAttribute("msg", "데이터조회 실패");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
		
		
	}
	
	
	
	
	
//	modify.jsp -> mypage.jsp로 이동
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
				//성공 -> 마이페이지로 이동
//				response.sendRedirect("/member/detail.do?memberId=" + memberId);  //$는 JSP에서만 쓰는것
				redirect.addAttribute("memberId", memberId);  //redirect시 쿼리스트링 보내주는 법
				return "redirect:/member/mypage.do";  //$는 JSP에서만 쓰는것  , ?가 있을때는 +기로 값을 넣어줌
			} else {
				//실패 -> 에러페이지 이동
				model.addAttribute("msg", "정보수정 완료 실패");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}

		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
