package com.github.idelvan.ifood2.cadastro.infra;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDTOValidator implements ConstraintValidator<ValidDTO, DTO> {

    @Override
    public boolean isValid(DTO dto, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        return dto.isValid(context);
    }

}
