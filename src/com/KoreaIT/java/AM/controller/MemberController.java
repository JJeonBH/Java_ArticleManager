package com.KoreaIT.java.AM.controller;

import java.util.Scanner;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.service.MemberService;
import com.KoreaIT.java.AM.util.Util;

public class MemberController extends Controller {
	private Scanner sc;
	private MemberService memberService;

	public MemberController(Scanner sc) {
		this.sc = sc;
		this.memberService = Container.memberService;
	}

	public void doAction(String cmd, String actionMethodName) {

		switch (actionMethodName) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		default:
			System.out.println("존재하지 않는 명령어입니다");
			break;
		}
	}

	private void doLogout() {
		loginedMember = null;
		System.out.println("로그아웃 되었습니다");
	}

	private void doLogin() {
		System.out.printf("로그인 아이디 : ");
		String loginId = sc.nextLine();
		System.out.printf("로그인 비밀번호 : ");
		String loginPw = sc.nextLine();

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			System.out.println("존재하지 않는 회원입니다");
			return;
		}

		if (member.loginPw.equals(loginPw) == false) {
			System.out.println("비밀번호를 확인하세요");
			return;
		}

		loginedMember = member;
		System.out.printf("%s님이 로그인 했습니다\n", loginedMember.name);

	}

	public void doJoin() {
		int id = memberService.getNewId();

		String regDate = Util.getNowDateStr();

		String loginId = null;
		while (true) {
			System.out.printf("로그인 아이디 : ");
			loginId = sc.nextLine();

			if (memberService.isJoinableLoginId(loginId) == false) {
				System.out.printf("%s(은)는 사용중인 아이디입니다\n", loginId);
				continue;
			}
			break;
		}

		String loginPw = null;
		String loginPwCheck = null;
		while (true) {
			System.out.printf("로그인 비밀번호 : ");
			loginPw = sc.nextLine();
			System.out.printf("로그인 비밀번호 확인 : ");
			loginPwCheck = sc.nextLine();

			if (loginPw.equals(loginPwCheck) == false) {
				System.out.println("비밀번호를 다시 입력하세요");
				continue;
			}
			break;
		}

		System.out.printf("이름 : ");
		String name = sc.nextLine();

		Member member = new Member(id, regDate, loginId, loginPw, name);
		memberService.add(member);

		System.out.printf("%d번 회원이 가입했습니다\n", id);
	}

	public void makeTestData() {
		memberService.makeTestData();
	}

}