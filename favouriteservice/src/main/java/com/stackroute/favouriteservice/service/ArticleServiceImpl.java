package com.stackroute.favouriteservice.service;

import static java.util.Objects.isNull;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.favouriteservice.entity.ArticleEntity;
import com.stackroute.favouriteservice.exception.ArticleAllreadyExistsException;
import com.stackroute.favouriteservice.exception.ArticleNotFoundException;
import com.stackroute.favouriteservice.repository.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleRepository articleRepository;
	

	@Override
	public List<ArticleEntity> deleteArticle(String author,String publishedAt) throws ArticleNotFoundException {
		List<ArticleEntity> articles = null;
		  if(!StringUtils.isEmpty(author) && !"null".equalsIgnoreCase(author) && !StringUtils.isBlank(publishedAt)) {
			  ArticleEntity articleToDelete = articleRepository.findByAuthorAndPublishedAt(author,publishedAt);
			  String userId = articleToDelete.getUserId();
			  articleRepository.delete(articleToDelete);
			  articles  = getMyArticles(userId);
			  }
		  else if(!StringUtils.isBlank(publishedAt)){
			  ArticleEntity articleToDelete = articleRepository.findByPublishedAt(publishedAt);
			  String userId = articleToDelete.getUserId();
			  articleRepository.delete(articleToDelete);
			  articles  = getMyArticles(userId);
		  }
		  
		return articles;
	}

	@Override
	public List<ArticleEntity> getMyArticles(String userId) {
		 List<ArticleEntity> articles = null;
		  if(!StringUtils.isBlank(userId)) {
			  articles = articleRepository.findByUserId(userId);
		  }
		return articles;
	}

	@Override
	public boolean saveArticle(ArticleEntity articleEntity) throws ArticleAllreadyExistsException {
		boolean isSaved = false;
		if(isNull(articleRepository.findByAuthorAndPublishedAt(articleEntity.getAuthor(), articleEntity.getPublishedAt()))) {
			articleRepository.saveAndFlush(articleEntity);
			isSaved =true;
		}
		else {
			throw new ArticleAllreadyExistsException("This Aricle  has been already added for the current user.");
		}
		
		return isSaved;
	}

}
