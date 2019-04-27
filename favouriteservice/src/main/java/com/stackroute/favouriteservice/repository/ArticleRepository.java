package com.stackroute.favouriteservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stackroute.favouriteservice.entity.ArticleEntity;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {
	
	List<ArticleEntity> findByUserId(String userId);
	ArticleEntity  findByAuthorAndPublishedAt(String author,String publishedAt);
	ArticleEntity findByPublishedAt (String publishedAt);

}
