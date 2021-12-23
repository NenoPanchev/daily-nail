package project.dailynail.models.service;

import java.time.LocalDateTime;

public class ArticleServiceModel extends BaseServiceModel {
    private String title;
    private String url;
    private String imageUrl;
    private String text;
    private LocalDateTime created;
    private LocalDateTime posted;
    private boolean activated;
    private String categoryName;

}
