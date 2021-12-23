package project.dailynail.models.validators;

import project.dailynail.models.service.ArticleCreateServiceModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ArticleImageServiceValidator implements ConstraintValidator<ArticleImageService, ArticleCreateServiceModel> {
    private String firstField;
    private String secondField;
    private String message;

    @Override
    public void initialize(ArticleImageService constraintAnnotation) {
        this.firstField = constraintAnnotation.first();
        this.secondField = constraintAnnotation.second();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(ArticleCreateServiceModel articleCreateServiceModel, ConstraintValidatorContext constraintValidatorContext) {
        boolean noUrl = articleCreateServiceModel.getImageUrl().isEmpty();
        boolean noFile = articleCreateServiceModel.getImageFile().getContentType().equals("application/octet-stream");
        boolean isValid = !noUrl || !noFile;

        if (!isValid) {

            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstField)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return isValid;
    }
}
