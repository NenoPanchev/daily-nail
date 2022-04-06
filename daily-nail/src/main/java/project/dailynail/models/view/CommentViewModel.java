package project.dailynail.models.view;

public class CommentViewModel {
    private String id;
    private String text;
    private String authorFullName;
    private Integer likes;
    private Integer dislikes;
    private String articleId;
    private String timePosted;

    public CommentViewModel() {
    }

    public String getText() {
        return text;
    }

    public CommentViewModel setText(String text) {
        this.text = text;
        return this;
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public CommentViewModel setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
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

    public String getTimePosted() {
        return timePosted;
    }

    public CommentViewModel setTimePosted(String timePosted) {
        this.timePosted = timePosted;
        return this;
    }

    public String getId() {
        return id;
    }

    public CommentViewModel setId(String id) {
        this.id = id;
        return this;
    }
}
