package com.bikkadIt.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Annotation;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value.isBlank()){
            return false;
        }
        else {
            return true;
        }

    }
}
