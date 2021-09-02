package project.dailynail.models.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class ImageMultipartFileBindingModel {
    private MultipartFile multipartFile;

    public ImageMultipartFileBindingModel() {
    }

    @NotNull(message = "Submit an image file")
    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public ImageMultipartFileBindingModel setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
        return this;
    }
}
