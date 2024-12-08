package com.wanjerkheda.blog.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private String title;
    private String content;
    private LocalDate date;
}
