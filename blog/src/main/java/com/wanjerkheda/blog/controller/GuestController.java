package com.wanjerkheda.blog.controller;

import com.wanjerkheda.blog.entity.Article;
import com.wanjerkheda.blog.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class GuestController {

    private final ArticleService articleService;

    @GetMapping("/")
    public String home(Model model) {
        List<Article> articleList = articleService.getAllArticles();
        model.addAttribute("articles", articleList);
        return "home";
    }

    @GetMapping("/article/{title}")
    public String fetchArticle(@PathVariable String title, Model model) {
        List<Article> articleList = articleService.getAllArticles();
        for (Article article : articleList) {
            if (article.getTitle().replace(" ", "_").equalsIgnoreCase(title)) {
                model.addAttribute("article", article);
                return "article";
            }
        }
        return "404";
    }
}
