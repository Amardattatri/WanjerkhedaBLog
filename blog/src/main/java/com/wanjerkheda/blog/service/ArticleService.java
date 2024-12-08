package com.wanjerkheda.blog.service;

import com.wanjerkheda.blog.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {

    List<Article> getAllArticles();

    void addArticle(Article article);

    void deleteArticle(String article);
}
