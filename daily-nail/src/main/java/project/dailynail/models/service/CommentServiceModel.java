package project.dailynail.models.service;

import java.time.LocalDateTime;

public class CommentServiceModel extends BaseServiceModel{
    private String id;
    private String text;
    private UserServiceModel author;
    private Integer likes;
    private Integer dislikes;
    private ArticleServiceModel article;
    private LocalDateTime timePosted;

    public CommentServiceModel() {
    }

    public String getText() {
        return text;
    }

    public CommentServiceModel setText(String text) {
        this.text = text;
        return this;
    }

    public UserServiceModel getAuthor() {
        return author;
    }

    public CommentServiceModel setAuthor(UserServiceModel author) {
        this.author = author;
        return this;
    }

    public Integer getLikes() {
        return likes;
    }

    public CommentServiceModel setLikes(Integer likes) {
        this.likes = likes;
        return this;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public CommentServiceModel setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
        return this;
    }

    public ArticleServiceModel getArticle() {
        return article;
    }

    public CommentServiceModel setArticle(ArticleServiceModel article) {
        this.article = article;
        return this;
    }

    public LocalDateTime getTimePosted() {
        return timePosted;
    }

    public CommentServiceModel setTimePosted(LocalDateTime timePosted) {
        this.timePosted = timePosted;
        return this;
    }

    @Override
    public String toString() {
        return "CommentServiceModel{" +
                "text='" + text + '\'' +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", timePosted=" + timePosted +
                '}';
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public CommentServiceModel setId(String id) {
        this.id = id;
        return this;
    }
}
