package com.stackroute.favouriteservice.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ARTICLE", catalog = "NEWSDB")
public class ArticleEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5443962551589117394L;

	@Id
	@Column(name = "ARTICLE_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name = "ARTICLE_AUTHOR")
	private String author;

	@Column(name = "ARTICLE_TITLE")
	private String title;
	
	@Column(name = "ARTICLE_DESCRIPTION")
	private String description;
	
	@Column(name = "ARTICLE_URL")
	private String url;
	
	@Column(name = "ARTICLE_URL_IMAGE")
	private String urlToImage;
	
	@Column(name = "ARTICLE_CONTENT")
	private String content;
	
	@Column(name = "PUBLISHED_AT")
	private String publishedAt;
	
	@Column(name="USER_ID")
	private String userId;
	
	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="ID")
	private SourceEntity sourceEntity;
	
	@Column(name="is_added")
	private String isAdded;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrltoImage() {
		return urlToImage;
	}

	public void setUrltoImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	

	public String getUrlToImage() {
		return urlToImage;
	}

	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	public SourceEntity getSourceEntity() {
		return sourceEntity;
	}

	public void setSourceEntity(SourceEntity sourceEntity) {
		this.sourceEntity = sourceEntity;
	}

	
	public String getIsAdded() {
		return isAdded;
	}

	public void setIsAdded(String isAdded) {
		this.isAdded = isAdded;
	}

	@Override
	public String toString() {
		return "ArticleEntity [id=" + id + ", author=" + author + ", title=" + title + ", description=" + description
				+ ", url=" + url + ", urlToImage=" + urlToImage + ", content=" + content + ", publishedAt="
				+ publishedAt + ", userId=" + userId + ", sourceEntity=" + sourceEntity + ", isAdded=" + isAdded + "]";
	}

	

	

	

}
