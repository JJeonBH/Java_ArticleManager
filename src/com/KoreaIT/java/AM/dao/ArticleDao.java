package com.KoreaIT.java.AM.dao;

import java.util.ArrayList;
import java.util.List;

import com.KoreaIT.java.AM.dto.Article;

public class ArticleDao extends Dao {
	private List<Article> articles;

	public ArticleDao() {
		articles = new ArrayList<>();
	}

	public void add(Article article) {
		articles.add(article);
		lastId++;
	}

	public int size() {
		return articles.size();
	}

	public void remove(Article foundArticle) {
		articles.remove(foundArticle);
	}

	public Article getArticleById(int id) {
		int index = getArticleIndexById(id);
		if (index != -1) {
			return articles.get(index);
		}

		return null;
	}

	public int getArticleIndexById(int id) {
		int i = 0;

		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}
			i++;
		}

		return -1;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public List<Article> getArticles(String searchKeyword) {
		List<Article> forPrintArticles = articles;
		if (searchKeyword != null) {
			forPrintArticles = new ArrayList<>();

			for (Article article : articles) {
				if (article.title.contains(searchKeyword)) {
					forPrintArticles.add(article);
				}
			}
			if (forPrintArticles.size() == 0) {
				System.out.println("찾는 게시물이 없습니다");
				return null;
			}

			return forPrintArticles;
		}

		return forPrintArticles;
	}

}