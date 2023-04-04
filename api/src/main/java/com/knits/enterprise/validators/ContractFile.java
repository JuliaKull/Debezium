package com.knits.enterprise.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ContractFileValidator.class})
public @interface ContractFile {
    String message() default "Only docx, zip and pdf type files allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
