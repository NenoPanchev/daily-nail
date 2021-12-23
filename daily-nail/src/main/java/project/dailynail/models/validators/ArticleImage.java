package project.dailynail.models.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ArticleImageValidator.class)
public @interface ArticleImage {
    String first();
    String second();

    String message() default "You must upload an image";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
