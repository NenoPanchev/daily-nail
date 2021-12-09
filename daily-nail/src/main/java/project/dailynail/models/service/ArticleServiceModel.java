package project.dailynail.models.service;

import project.dailynail.models.entities.UserEntity;

import java.time.LocalDateTime;

public class ArticleServiceModel extends BaseServiceModel {
    private String title;
    private UserEntity author;
    private String url;
    private String imageUrl;
    private String text;
    private LocalDateTime created;
    private LocalDateTime posted;
    private boolean activated;
    private String categoryName;

}
