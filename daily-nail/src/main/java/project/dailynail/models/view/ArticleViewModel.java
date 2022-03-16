package project.dailynail.models.view;

import java.util.List;

public class ArticleViewModel {
    private String id;
    private String title;
    private String url;
    private String text;
    private String authorFullName;
    private String imageUrl;
    private String created;
    private String posted;
    private boolean activated;
    private String categoryName;
    private String subCategoryName;
    private boolean disabledComments;
    private List<CommentViewModel> comments;
    private Integer seen;

    public ArticleViewModel() {
    }

    public String getId() {
        return id;
    }

    public ArticleViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ArticleViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ArticleViewModel setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getText() {
        return text;
    }

    public ArticleViewModel setText(String text) {
        this.text = text;
        return this;
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public ArticleViewModel setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArticleViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public ArticleViewModel setCreated(String created) {
        this.created = created;
        return this;
    }

    public String getPosted() {
        return posted;
    }

    public ArticleViewModel setPosted(String posted) {
        this.posted = posted;
        return this;
    }

    public boolean isActivated() {
        return activated;
    }

    public ArticleViewModel setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ArticleViewModel setCategoryName(String category) {
        this.categoryName = category;
        return this;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public ArticleViewModel setSubCategoryName(String subCategory) {
        this.subCategoryName = subCategory;
        return this;
    }

    public boolean isDisabledComments() {
        return disabledComments;
    }

    public ArticleViewModel setDisabledComments(boolean disabledComments) {
        this.disabledComments = disabledComments;
        return this;
    }

    public List<CommentViewModel> getComments() {
        return comments;
    }

    public ArticleViewModel setComments(List<CommentViewModel> comments) {
        this.comments = comments;
        return this;
    }

    public Integer getSeen() {
        return seen;
    }

    public ArticleViewModel setSeen(Integer seen) {
        this.seen = seen;
        return this;
    }
}
