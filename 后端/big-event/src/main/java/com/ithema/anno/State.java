package com.ithema.anno;

import com.ithema.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;

@Documented
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StateValidation.class})
public @interface State {
    String message() default "state值只能是已发布或者草稿";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
