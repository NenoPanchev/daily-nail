package project.dailynail.models.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
public class ArticleEntity extends BaseEntity {
    private String title;
    private String author;
    private String imageUrl;
    private String content;
    private LocalDateTime created;
    private LocalDateTime posted;
    private boolean activated;
    private CategoryEntity category;
    private SubcategoryEntity subcategory;

    public ArticleEntity() {
        this.activated = false;
    }

    public String getTitle() {
        return title;
    }

    public ArticleEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ArticleEntity setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArticleEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ArticleEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public ArticleEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getPosted() {
        return posted;
    }

    public ArticleEntity setPosted(LocalDateTime posted) {
        this.posted = posted;
        return this;
    }

    public boolean isActivated() {
        return activated;
    }

    public ArticleEntity setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }

    @ManyToOne
    public CategoryEntity getCategory() {
        return category;
    }

    public ArticleEntity setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }

    @ManyToOne
    public SubcategoryEntity getSubcategory() {
        return subcategory;
    }

    public ArticleEntity setSubcategory(SubcategoryEntity subcategory) {
        this.subcategory = subcategory;
        return this;
    }
}
