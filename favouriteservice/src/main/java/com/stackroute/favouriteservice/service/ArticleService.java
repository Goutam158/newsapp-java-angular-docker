package com.stackroute.favouriteservice.service;

import java.util.List;

import com.stackroute.favouriteservice.entity.ArticleEntity;
import com.stackroute.favouriteservice.exception.ArticleAllreadyExistsException;
import com.stackroute.favouriteservice.exception.ArticleNotFoundException;

public interface ArticleService {

	boolean saveArticle(ArticleEntity articleEntity) throws ArticleAllreadyExistsException;

	List<ArticleEntity> deleteArticle(String author,String publishedAt) throws ArticleNotFoundException;

	List<ArticleEntity> getMyArticles(String userId);

}
