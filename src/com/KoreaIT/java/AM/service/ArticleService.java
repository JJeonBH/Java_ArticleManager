package com.KoreaIT.java.AM.service;

import java.util.List;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dao.ArticleDao;
import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.util.Util;

public class ArticleService {
	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;
	}

	public void add(Article article) {
		articleDao.add(article);
	}

	public int size() {
		return articleDao.size();
	}

	public void remove(Article foundArticle) {
		articleDao.remove(foundArticle);
	}

	public int getNewId() {
		return articleDao.getNewId();
	}

	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public int getArticleIndexById(int id) {
		return articleDao.getArticleIndexById(id);
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public List<Article> getArticles(String searchKeyword) {
		List<Article> articles = articleDao.getArticles(searchKeyword);

		return articles;
	}

	public void makeTestData() {
		System.out.println("테스트를 위한 게시물 데이터를 생성합니다");
		articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 1, "title 1", "body 1", 11));
		articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 2, "title 2", "body 2", 22));
		articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDateStr(), 3, "title 3", "body 3", 33));
	}

}