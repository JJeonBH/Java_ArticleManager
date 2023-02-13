package com.KoreaIT.java.AM.service;

import java.util.List;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dao.MemberDao;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

public class MemberService {
	private MemberDao memberDao;

	public MemberService() {
		memberDao = Container.memberDao;
	}

	public void add(Member member) {
		memberDao.add(member);
	}

	public int getNewId() {
		return memberDao.getNewId();
	}

	public boolean isJoinableLoginId(String loginId) {
		return memberDao.isJoinableLoginId(loginId);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	public int getMemberIndexByLoginId(String loginId) {
		return memberDao.getMemberIndexByLoginId(loginId);
	}

	public List<Member> getMembers() {
		return memberDao.getMembers();
	}

	public void makeTestData() {
		System.out.println("테스트를 위한 회원 데이터를 생성합니다");
		memberDao.add(new Member(Container.memberDao.getNewId(), Util.getNowDateStr(), "admin", "admin", "관리자"));
		memberDao.add(new Member(Container.memberDao.getNewId(), Util.getNowDateStr(), "test1", "test1", "김영희"));
		memberDao.add(new Member(Container.memberDao.getNewId(), Util.getNowDateStr(), "test2", "test2", "민병관"));
	}

}