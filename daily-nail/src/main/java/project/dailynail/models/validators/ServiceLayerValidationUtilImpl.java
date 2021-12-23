package project.dailynail.models.validators;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ServiceLayerValidationUtilImpl implements ServiceLayerValidationUtil{
    private Validator validator;

    public ServiceLayerValidationUtilImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <T> void validate(T entity) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error occured: ");
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(sb::append);

            throw new ConstraintViolationException(sb.toString(), violations);
        }
    }
}
