package project.dailynail.models.view;

public class ArticleImageAndTitleViewModel {
    private String id;
    private String imageUrl;
    private String title;
    private String url;

    public ArticleImageAndTitleViewModel() {
    }

    public String getId() {
        return id;
    }

    public ArticleImageAndTitleViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArticleImageAndTitleViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ArticleImageAndTitleViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ArticleImageAndTitleViewModel setUrl(String url) {
        this.url = url;
        return this;
    }
}
