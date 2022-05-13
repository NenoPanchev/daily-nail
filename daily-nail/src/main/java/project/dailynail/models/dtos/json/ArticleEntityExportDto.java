package project.dailynail.models.dtos.json;

import com.google.gson.annotations.Expose;

public class ArticleEntityExportDto {
    @Expose
    private String title;
    @Expose
    private String authorEmail;
    @Expose
    private String url;
    @Expose
    private String imageUrl;
    @Expose
    private String text;
    @Expose
    private String created;
    @Expose
    private String posted;
    @Expose
    private boolean activated;
    @Expose
    private String categoryName;
    @Expose
    private String subcategoryName;
    @Expose
    private boolean disabledComments;
    @Expose
    private Integer seen;
    @Expose
    private boolean top;

    public ArticleEntityExportDto() {
    }

    public String getTitle() {
        return title;
    }

    public ArticleEntityExportDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public ArticleEntityExportDto setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ArticleEntityExportDto setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArticleEntityExportDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getText() {
        return text;
    }

    public ArticleEntityExportDto setText(String text) {
        this.text = text;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public ArticleEntityExportDto setCreated(String created) {
        this.created = created;
        return this;
    }

    public String getPosted() {
        return posted;
    }

    public ArticleEntityExportDto setPosted(String posted) {
        this.posted = posted;
        return this;
    }

    public boolean isActivated() {
        return activated;
    }

    public ArticleEntityExportDto setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ArticleEntityExportDto setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public ArticleEntityExportDto setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
        return this;
    }

    public boolean isDisabledComments() {
        return disabledComments;
    }

    public ArticleEntityExportDto setDisabledComments(boolean disabledComments) {
        this.disabledComments = disabledComments;
        return this;
    }

    public Integer getSeen() {
        return seen;
    }

    public ArticleEntityExportDto setSeen(Integer seen) {
        this.seen = seen;
        return this;
    }

    public boolean isTop() {
        return top;
    }

    public ArticleEntityExportDto setTop(boolean top) {
        this.top = top;
        return this;
    }
}
