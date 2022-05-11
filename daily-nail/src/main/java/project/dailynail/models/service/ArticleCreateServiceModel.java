package project.dailynail.models.service;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import project.dailynail.models.validators.ArticleImage;
import project.dailynail.models.validators.ArticleImageService;
import project.dailynail.models.validators.ValidImage;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@ArticleImageService(
        first = "imageUrl",
        second = "imageFile"
)
public class ArticleCreateServiceModel {
    private String title;
    private String imageUrl;
    private MultipartFile imageFile;
    private String text;
    private LocalDateTime posted;
    private String categoryName;
    private String disabledComments;
    private String top;

    public ArticleCreateServiceModel() {
    }

    @Size(min = 12, max = 150, message = "Title must be between 12 and 150 characters")
    public String getTitle() {
        return title;
    }

    public ArticleCreateServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    @Pattern(regexp = "^http.*\\.(jpg|png|jpeg)$|^\\s*$", message = "You must enter a valid url address")
    public String getImageUrl() {
        return imageUrl;
    }

    public ArticleCreateServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @ValidImage
    public MultipartFile getImageFile() {
        return imageFile;
    }

    public ArticleCreateServiceModel setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
        return this;
    }

    @Size(min = 100, max = 10500, message = "Text must be between 100 and 10500 characters")
    public String getText() {
        return text;
    }

    public ArticleCreateServiceModel setText(String text) {
        this.text = text;
        return this;
    }

    @FutureOrPresent(message = "Must be a date/time in the present or in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    public LocalDateTime getPosted() {
        return posted;
    }

    public ArticleCreateServiceModel setPosted(LocalDateTime posted) {
        this.posted = posted;
        return this;
    }

    @Pattern(regexp = "^(?!Select Category$).*$", message = "You must select a category")
    public String getCategoryName() {
        return categoryName;
    }

    public ArticleCreateServiceModel setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    @Pattern(regexp = "^Yes|No$", message = "Enter a valid option")
    public String getDisabledComments() {
        return disabledComments;
    }

    public ArticleCreateServiceModel setDisabledComments(String disabledComments) {
        this.disabledComments = disabledComments;
        return this;
    }

    @Pattern(regexp = "^Yes|No$", message = "Enter a valid option")
    public String getTop() {
        return top;
    }

    public ArticleCreateServiceModel setTop(String top) {
        this.top = top;
        return this;
    }
}
