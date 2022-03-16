package project.dailynail.models.view;

import java.time.LocalDateTime;

public class CommentViewModel {
    private String text;
    private String author;
    private Integer likes;
    private Integer dislikes;
    private String articleId;
    private LocalDateTime timePosted;

    public CommentViewModel() {
    }

    public String getText() {
        return text;
    }

    public CommentViewModel setText(String text) {
        this.text = text;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public CommentViewModel setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Integer getLikes() {
        return likes;
    }

    public CommentViewModel setLikes(Integer likes) {
        this.likes = likes;
        return this;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public CommentViewModel setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
        return this;
    }

    public String getArticleId() {
        return articleId;
    }

    public CommentViewModel setArticleId(String articleId) {
        this.articleId = articleId;
        return this;
    }

    public LocalDateTime getTimePosted() {
        return timePosted;
    }

    public CommentViewModel setTimePosted(LocalDateTime timePosted) {
        this.timePosted = timePosted;
        return this;
    }
}
