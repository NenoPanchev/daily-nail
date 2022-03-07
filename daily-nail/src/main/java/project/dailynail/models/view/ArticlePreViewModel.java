package project.dailynail.models.view;

public class ArticlePreViewModel {
    private String id;
    private String imageUrl;
    private String title;
    private String text;
    private String url;

    public ArticlePreViewModel() {
    }

    public String getId() {
        return id;
    }

    public ArticlePreViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArticlePreViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ArticlePreViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }

    public ArticlePreViewModel setText(String text) {
        this.text = text;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ArticlePreViewModel setUrl(String url) {
        this.url = url;
        return this;
    }
}
