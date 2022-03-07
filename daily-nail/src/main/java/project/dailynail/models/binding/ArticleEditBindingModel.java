package project.dailynail.models.binding;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import project.dailynail.models.validators.ValidImage;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class ArticleEditBindingModel {
    private String id;
    private String title;
    private String imageUrl;
    private MultipartFile imageFile;
    private String text;
    private boolean activated;
    private LocalDateTime posted;
    private String categoryName;
    private String disabledComments;
    private String top;

    public ArticleEditBindingModel() {
    }

    @Size(min = 12, max = 150, message = "Title must be between 12 and 150 characters")
    public String getTitle() {
        return title;
    }

    public ArticleEditBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    @Pattern(regexp = "^http.*\\.(jpg|png)$|^\\s*$", message = "You must enter a valid url address")
    public String getImageUrl() {
        return imageUrl;
    }

    public ArticleEditBindingModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @ValidImage
    public MultipartFile getImageFile() {
        return imageFile;
    }

    public ArticleEditBindingModel setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
        return this;
    }

    @Size(min = 100, max = 5500, message = "Text must be between 100 and 5500 characters")
    public String getText() {
        return text;
    }

    public ArticleEditBindingModel setText(String text) {
        this.text = text;
        return this;
    }


    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    public LocalDateTime getPosted() {
        return posted;
    }

    public ArticleEditBindingModel setPosted(LocalDateTime posted) {
        this.posted = posted;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @NotEmpty
    public ArticleEditBindingModel setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    @Pattern(regexp = "^Yes|No$", message = "Enter a valid option")
    public String getDisabledComments() {
        return disabledComments;
    }

    public ArticleEditBindingModel setDisabledComments(String disabledComments) {
        this.disabledComments = disabledComments;
        return this;
    }

    public boolean isActivated() {
        return activated;
    }

    public ArticleEditBindingModel setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public String getId() {
        return id;
    }

    public ArticleEditBindingModel setId(String id) {
        this.id = id;
        return this;
    }

    @Pattern(regexp = "^Yes|No$", message = "Enter a valid option")
    public String getTop() {
        return top;
    }

    public ArticleEditBindingModel setTop(String top) {
        this.top = top;
        return this;
    }
}
