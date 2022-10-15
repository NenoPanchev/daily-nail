package project.dailynail.models.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class ArticleServiceModel extends BaseServiceModel {
    private String title;
    private UserServiceModel author;
    private String url;
    private String imageUrl;
    private String text;
    private LocalDateTime created;
    private LocalDateTime posted;
    private boolean activated;
    private CategoryServiceModel category;
    private SubcategoryServiceModel subcategory;
    private boolean disabledComments;
    private boolean top;
    private Integer seen;
    private List<CommentServiceModel> comments;

    public ArticleServiceModel() {
    }

    public String getTitle() {
        return title;
    }

    public ArticleServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public UserServiceModel getAuthor() {
        return author;
    }

    public ArticleServiceModel setAuthor(UserServiceModel author) {
        this.author = author;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ArticleServiceModel setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArticleServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getText() {
        return text;
    }

    public ArticleServiceModel setText(String text) {
        this.text = text;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public ArticleServiceModel setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getPosted() {
        return posted;
    }

    public ArticleServiceModel setPosted(LocalDateTime posted) {
        this.posted = posted;
        return this;
    }

    public boolean isActivated() {
        return activated;
    }

    public ArticleServiceModel setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }



    public boolean isDisabledComments() {
        return disabledComments;
    }

    public ArticleServiceModel setDisabledComments(boolean disabledComments) {
        this.disabledComments = disabledComments;
        return this;
    }

    public List<CommentServiceModel> getComments() {
        return comments;
    }

    public ArticleServiceModel setComments(List<CommentServiceModel> comments) {
        this.comments = comments;
        return this;
    }

    @Override
    public String toString() {
        return "ArticleServiceModel{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", text='" + text + '\'' +
                ", created=" + created +
                ", posted=" + posted +
                ", activated=" + activated +
                ", category=" + category +
                ", subcategory=" + subcategory +
                ", disabledComments=" + disabledComments +
                '}';
    }

    public Integer getSeen() {
        return seen;
    }

    public ArticleServiceModel setSeen(Integer seen) {
        this.seen = seen;
        return this;
    }

    public CategoryServiceModel getCategory() {
        return category;
    }

    public ArticleServiceModel setCategory(CategoryServiceModel category) {
        this.category = category;
        return this;
    }

    public SubcategoryServiceModel getSubcategory() {
        return subcategory;
    }

    public ArticleServiceModel setSubcategory(SubcategoryServiceModel subcategory) {
        this.subcategory = subcategory;
        return this;
    }

    public boolean isTop() {
        return top;
    }

    public ArticleServiceModel setTop(boolean top) {
        this.top = top;
        return this;
    }
}
