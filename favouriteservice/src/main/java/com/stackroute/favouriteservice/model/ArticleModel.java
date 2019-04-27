package com.stackroute.favouriteservice.model;

public class ArticleModel {
	
	private String author;

	private String title;
	
	private String description;
	
	private String url;
	
	private String urlToImage;
	
	private String content;
	
	private String publishedAt;
	
	private String userId;
	
	private SourceModel sourceModel;
	
	private String isAdded;

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

	public String getUrlToImage() {
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

	public SourceModel getSourceModel() {
		return sourceModel;
	}

	public void setSourceModel(SourceModel sourceModel) {
		this.sourceModel = sourceModel;
	}

	
	public String getIsAdded() {
		return isAdded;
	}

	public void setIsAdded(String isAdded) {
		this.isAdded = isAdded;
	}

	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	@Override
	public String toString() {
		return "ArticleModel [author=" + author + ", title=" + title + ", description=" + description + ", url=" + url
				+ ", urlToImage=" + urlToImage + ", content=" + content + ", publishedAt=" + publishedAt + ", userId="
				+ userId + ", sourceModel=" + sourceModel + ", isAdded=" + isAdded + "]";
	}

	
	
	

}
