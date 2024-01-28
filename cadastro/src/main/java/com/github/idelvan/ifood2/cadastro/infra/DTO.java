package com.github.idelvan.ifood2.cadastro.infra;

import jakarta.validation.ConstraintValidatorContext;

public interface DTO {

    default boolean isValid(ConstraintValidatorContext constraintValidatorContext){
        return true;
    }
    
}
