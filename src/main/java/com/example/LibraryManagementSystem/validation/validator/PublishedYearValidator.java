package com.example.LibraryManagementSystem.validation.validator;

import com.example.LibraryManagementSystem.validation.annotation.ValidPublishedYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

import static com.example.LibraryManagementSystem.constants.BookServiceConstants.MIN_VALID_PUBLISHED_YEAR;

public class PublishedYearValidator implements ConstraintValidator<ValidPublishedYear, Integer> {

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        if (year == null) return false;

        int currentYear = Year.now().getValue();

        boolean isValid = year >= MIN_VALID_PUBLISHED_YEAR && year <= currentYear;
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            //only way to add custom message as @AssertTrue does not supports only static message
            context.buildConstraintViolationWithTemplate(
                    "Published year must be between " + MIN_VALID_PUBLISHED_YEAR + " and " + currentYear
            ).addConstraintViolation();
        }
        return isValid;
    }
}