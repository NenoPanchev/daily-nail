package project.dailynail.models.validators;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        String contentType = multipartFile.getContentType();
        if (!isSupportedContentType(contentType)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Only JPG and PNG images are allowed")
                    .addConstraintViolation();
            return false;
        }


        return true;
    }

    private boolean isSupportedContentType(String contentType) {
        List<String> supportedContents = List.of("image/jpg", "image/jpeg", "image/bmp", "image/png", "application/octet-stream");
        return supportedContents.contains(contentType);
    }
}
