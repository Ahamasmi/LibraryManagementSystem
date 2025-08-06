package com.example.LibraryManagementSystem.validation.annotation;

import com.example.LibraryManagementSystem.validation.validator.PublishedYearValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.LibraryManagementSystem.constants.BookServiceConstants.MIN_VALID_PUBLISHED_YEAR;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PublishedYearValidator.class)
public @interface ValidPublishedYear {
    String message() default "Published year must be between " + MIN_VALID_PUBLISHED_YEAR + " and the current year";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    boolean isUpdate() default false; //differentiate between create and update validations
}