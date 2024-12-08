package com.wanjerkheda.blog.controller;

import com.wanjerkheda.blog.entity.Article;
import com.wanjerkheda.blog.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final ArticleService articleService;

    @Value("${ADMIN_USERNAME}")
    private String adminUserName;
    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password, HttpSession httpSession) {
        if (adminUserName.equals(username) && adminPassword.equals(password)) {
            httpSession.setAttribute("user", username);
            return "redirect:/admin/dashboard";
        }
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("user") == null) {
            return "login";
        }
        List<Article> articleList = articleService.getAllArticles();
        model.addAttribute("articles", articleList);
        return "dashboard";
    }

    @GetMapping("/add")
    public String addArticle() {
        return "addArticle";
    }

    @PostMapping("/add")
    public String addArticle(@RequestParam String title, @RequestParam String content) {
        Article article = new Article(title, content, LocalDate.now());
        articleService.addArticle(article);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/edit/{title}")
    public String editArticle(@PathVariable String title, Model model) {
        List<Article> articleList = articleService.getAllArticles();
        for (Article article : articleList) {
            if (article.getTitle().replace(" ", "_").equalsIgnoreCase(title)) {
                model.addAttribute("article", article);
                return "editArticle";
            }
        }
        return "404";
    }

    @PostMapping("/edit/{title}")
    public String editArticle(@PathVariable String title, @RequestParam String content) {
        Article article = new Article(title, content, LocalDate.now());
        articleService.deleteArticle(title);
        articleService.addArticle(article);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete/{title}")
    public String deleteArticle(@PathVariable String title) {
        articleService.deleteArticle(title);
        return "redirect:/admin/dashboard";
    }
}
