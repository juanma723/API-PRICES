package com.inditex.test.price.domain;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.format.DateTimeFormatter;

public class PriceRequestValidator implements ConstraintValidator<ValidPriceFilter, PriceFilter> {

    @Override
    public void initialize(ValidPriceFilter constraintAnnotation) {
    }

    @Override
    public boolean isValid(PriceFilter filter, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (filter.getProductId() == null || filter.getProductId() <= 0) {
            addConstraintViolation(context, "Product ID must be a positive number", "productId");
            isValid = false;
        }
        if (filter.getBrandId() == null || filter.getBrandId() <= 0) {
            addConstraintViolation(context, "Brand ID must be a positive number", "brandId");
            isValid = false;
        }

        if (filter.getUtc() == null || filter.getUtc() < -12 || filter.getUtc() > 14) {
            addConstraintViolation(context, "UTC must be between -12 and +14", "utc");
            isValid = false;
        }

        if (!isValidDate(filter.getDay())) {
            addConstraintViolation(context, "Day must be in the format yyyy-MM-dd", "day");
            isValid = false;
        }

        if (!isValidTime(filter.getTime())) {
            addConstraintViolation(context, "Time must be in the format HH:mm", "time");
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidDate(String day) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formatter.parse(day);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidTime(String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            formatter.parse(time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message, String field) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field)
                .addConstraintViolation();
    }
}
