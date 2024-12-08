package com.wanjerkheda.blog.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wanjerkheda.blog.entity.Article;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private static final String DIRECTORY = "articles/";


    public static List<Article> readArticles() {
        List<Article> articleList = new ArrayList<>();
        File folder = new File(DIRECTORY);
        if (folder.exists()) {
            for (File file : folder.listFiles()) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    Article article = objectMapper.readValue(file, Article.class);
                    articleList.add(article);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return articleList;
    }



    public static void writeArticle(Article article) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            String fileName = DIRECTORY
                    + article.getTitle().replace(" ", "_")
                    + ".json";
            objectMapper.writeValue(new File(fileName), article);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteArticle(String title) {
        String fileName =  title.replace(" ", "_") + ".json";
        try {
            Files.list(Paths.get(DIRECTORY))
                    .forEach(file -> {
                        if (file.getFileName().toString().equalsIgnoreCase(fileName)) {
                            file.toFile().delete();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
