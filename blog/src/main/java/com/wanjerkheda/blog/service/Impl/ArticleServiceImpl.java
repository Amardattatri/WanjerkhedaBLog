package com.wanjerkheda.blog.service.Impl;

import com.wanjerkheda.blog.entity.Article;
import com.wanjerkheda.blog.service.ArticleService;
import com.wanjerkheda.blog.util.FileUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {


    @Override
    public List<Article> getAllArticles() {
        return FileUtil.readArticles();
    }

    @Override
    public void addArticle(Article article) {
        FileUtil.writeArticle(article);
    }

    @Override
    public void deleteArticle(String title) {
        FileUtil.deleteArticle(title);
    }

}
