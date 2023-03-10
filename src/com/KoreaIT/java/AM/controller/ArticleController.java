package com.KoreaIT.java.AM.controller;

import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.service.ArticleService;
import com.KoreaIT.java.AM.util.Util;

public class ArticleController extends Controller {
	private String cmd;
	private Scanner sc;
	private ArticleService articleService;

	public ArticleController(Scanner sc) {
		this.sc = sc;
		this.articleService = Container.articleService;
	}

	public void doAction(String cmd, String actionMethodName) {
		this.cmd = cmd;

		switch (actionMethodName) {
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		case "write":
			doWrite();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		default:
			System.out.println("존재하지 않는 명령어입니다");
			break;
		}

	}

	private void doWrite() {
		int id = articleService.getNewId();

		String regDate = Util.getNowDateStr();
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		Article article = new Article(id, regDate, loginedMember.id, title, body);
		articleService.add(article);

		System.out.printf("%d번 글이 생성되었습니다\n", id);
	}

	private void showList() {
		if (articleService.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}

		String searchKeyword = cmd.substring("article list".length()).trim();

		List<Article> forPrintArticles = articleService.getArticles(searchKeyword);

		if (forPrintArticles == null) {
			return;
		}

		System.out.println("번호    |   작성자   |  제목  |    조회");
		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			Article article = forPrintArticles.get(i);

			String writerName = null;

			List<Member> members = Container.memberService.getMembers();

			for (Member member : members) {
				if (article.memberId == member.id) {
					writerName = member.name;
					break;
				}
			}

			System.out.printf("%4d  | %7s  | %4s |  %4d\n", article.id, writerName, article.title, article.viewCnt);
		}

	}

	private void showDetail() {
		String[] cmdBits = cmd.split(" ");

		int id = Integer.parseInt(cmdBits[2]);

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}

		foundArticle.increaseViewCnt();
		System.out.printf("번호 : %d\n", foundArticle.id);
		System.out.printf("날짜 : %s\n", foundArticle.regDate);
		System.out.printf("작성자 : %d\n", foundArticle.memberId);
		System.out.printf("제목 : %s\n", foundArticle.title);
		System.out.printf("내용 : %s\n", foundArticle.body);
		System.out.printf("조회 : %d\n", foundArticle.viewCnt);
	}

	private void doModify() {
		String[] cmdBits = cmd.split(" ");

		int id = Integer.parseInt(cmdBits[2]);

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}

		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("권한이 없습니다");
			return;
		}

		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		foundArticle.title = title;
		foundArticle.body = body;

		System.out.printf("%d번 게시물이 수정 되었습니다\n", id);
	}

	private void doDelete() {
		String[] cmdBits = cmd.split(" ");

		int id = Integer.parseInt(cmdBits[2]);

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}

		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("권한이 없습니다");
			return;
		}

		articleService.remove(foundArticle);
		System.out.printf("%d번 게시물이 삭제 되었습니다\n", id);
	}

	public void makeTestData() {
		articleService.makeTestData();
	}

}