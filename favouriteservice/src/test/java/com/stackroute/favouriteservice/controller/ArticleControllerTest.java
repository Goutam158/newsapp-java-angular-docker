package com.stackroute.favouriteservice.controller;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.stackroute.favouriteservice.entity.ArticleEntity;
import com.stackroute.favouriteservice.exception.ArticleAllreadyExistsException;
import com.stackroute.favouriteservice.exception.ArticleNotFoundException;
import com.stackroute.favouriteservice.model.ArticleModel;
import com.stackroute.favouriteservice.service.ArticleService;
import com.stackroute.favouriteservice.util.FavouriteServiceUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringRunner.class)
@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

	@Autowired
	MockMvc mockmvc;
	@MockBean
	ArticleService articleService;
	@Mock
	private Environment environment;
	
	List<ArticleEntity> entityList ;
	
	private String testUserEmail="test@gmail.com";
	
	@Before
	public void init() {
		
		this.entityList = new ArrayList<>();
		ArticleEntity  articleEntity1  = new ArticleEntity();
		articleEntity1.setAuthor("test1");
		articleEntity1.setPublishedAt(LocalDateTime.now().toString());
		articleEntity1.setDescription("test_desc_1");
		
		ArticleEntity  articleEntity2  = new ArticleEntity();
		articleEntity2.setAuthor("test2");
		articleEntity2.setPublishedAt(LocalDateTime.now().plusMinutes(5).toString());
		articleEntity2.setDescription("test_desc_2");
		
		entityList.add(articleEntity1);
		entityList.add(articleEntity2);
		
	}
	
	@Test
	public void test_whenAddArticle() throws ArticleAllreadyExistsException {
		when(articleService.saveArticle(any(ArticleEntity.class))).thenReturn(Boolean.TRUE);
		when(environment.getProperty("newsapp.jwt.secret.key")).thenReturn("mc&ll$m");
		try {
			this.mockmvc.perform(post("/news/api/v1/article")
					.header(HttpHeaders.AUTHORIZATION, generateToken(this.testUserEmail))
					.contentType(MediaType.APPLICATION_JSON)
                    .content(getJsonFromArticleModel(FavouriteServiceUtil.convertArticleEntityToModel(this.entityList.get(0)))))
			.andExpect(status().isOk())
			.andDo(result ->{
				assertThat(result.getResponse().getContentAsString()).
				isEqualTo("true");
			});
		}
		 catch(Exception ex) {
			 ex.printStackTrace();
		 }
	}
	@Test
	public void test_whenfetchArticles() {
		when(articleService.getMyArticles(anyString())).thenReturn(this.entityList);
		when(environment.getProperty("newsapp.jwt.secret.key")).thenReturn("mc&ll$m");
		try {
			this.mockmvc.perform(get("/news/api/v1/articles")
					.header(HttpHeaders.AUTHORIZATION, generateToken(this.testUserEmail))
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(result ->{
                List<ArticleModel> modelList = getArticleModelfromJsonList(result.getResponse().getContentAsString());
				 assertThat(modelList.size()).isEqualTo(2);
			});
		}
		 catch(Exception ex) {
			 ex.printStackTrace();
		 }
	}

	@Test
	public void test_whenDeleteArticle() throws  ArticleNotFoundException {
		this.entityList.remove(0);
		when(articleService.deleteArticle(anyString(),anyString())).thenReturn(this.entityList);
		when(environment.getProperty("newsapp.jwt.secret.key")).thenReturn("mc&ll$m");
		try {
			this.mockmvc.perform(delete("/news/api/v1/article")
					.header(HttpHeaders.AUTHORIZATION, generateToken(this.testUserEmail))
					.contentType(MediaType.APPLICATION_JSON)
					.param("author", this.entityList.get(0).getAuthor())
					.param("publishedAt", this.entityList.get(0).getPublishedAt()))
			.andExpect(status().isOk())
			.andDo(result ->{
                  List<ArticleModel> modelList = getArticleModelfromJsonList(result.getResponse().getContentAsString());
				 assertThat(modelList.size()).isEqualTo(1);
			});
		}
		 catch(Exception ex) {
			 ex.printStackTrace();
		 }
	}
	
	private static String generateToken(String email) {
		return "Bearer " + Jwts.builder().setSubject(email).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "mc&ll$m").compact();
	}

	private static List<ArticleModel> getArticleModelfromJsonList(String json) {
		List<ArticleModel> articleModelList = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			articleModelList = objectMapper.readValue(json, new TypeReference<List<ArticleModel>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return articleModelList;
	}

	private static ArticleModel getArticleModelfromJson(String json) {
		ArticleModel articleModel = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			articleModel = objectMapper.readValue(json, ArticleModel.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return articleModel;
	}

	private static String getJsonFromArticleModel(ArticleModel articleModel) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(articleModel);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

}
