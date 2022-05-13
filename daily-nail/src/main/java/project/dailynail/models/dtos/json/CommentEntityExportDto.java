package project.dailynail.models.dtos.json;

import com.google.gson.annotations.Expose;

public class CommentEntityExportDto {
    @Expose
    private String text;
    @Expose
    private String authorEmail;
    @Expose
    private Integer likes;
    @Expose
    private Integer dislikes;
    @Expose
    private String articleUrl;
    @Expose
    private String timePosted;

    public CommentEntityExportDto() {
    }

    public String getText() {
        return text;
    }

    public CommentEntityExportDto setText(String text) {
        this.text = text;
        return this;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public CommentEntityExportDto setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
        return this;
    }

    public Integer getLikes() {
        return likes;
    }

    public CommentEntityExportDto setLikes(Integer likes) {
        this.likes = likes;
        return this;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public CommentEntityExportDto setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
        return this;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public CommentEntityExportDto setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
        return this;
    }

    public String getTimePosted() {
        return timePosted;
    }

    public CommentEntityExportDto setTimePosted(String timePosted) {
        this.timePosted = timePosted;
        return this;
    }
}
