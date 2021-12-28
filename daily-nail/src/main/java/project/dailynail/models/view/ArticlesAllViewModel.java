package project.dailynail.models.view;


public class ArticlesAllViewModel {
    private String title;
    private String author;
    private String imageUrl;
    private String created;
    private String posted;
    private boolean activated;
    private String category;
    private Integer comments;
    private Integer seen;

    public ArticlesAllViewModel() {
    }

    public String getTitle() {
        return title;
    }

    public ArticlesAllViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ArticlesAllViewModel setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArticlesAllViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public ArticlesAllViewModel setCreated(String created) {
        this.created = created;
        return this;
    }

    public String getPosted() {
        return posted;
    }

    public ArticlesAllViewModel setPosted(String posted) {
        this.posted = posted;
        return this;
    }

    public boolean isActivated() {
        return activated;
    }

    public ArticlesAllViewModel setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ArticlesAllViewModel setCategory(String category) {
        this.category = category;
        return this;
    }

    public Integer getComments() {
        return comments;
    }

    public ArticlesAllViewModel setComments(Integer comments) {
        this.comments = comments;
        return this;
    }

    public Integer getSeen() {
        return seen;
    }

    public ArticlesAllViewModel setSeen(Integer seen) {
        this.seen = seen;
        return this;
    }
}
