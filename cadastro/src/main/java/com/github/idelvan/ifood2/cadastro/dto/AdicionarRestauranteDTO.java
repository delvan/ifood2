package com.github.idelvan.ifood2.cadastro.dto;

import com.github.idelvan.ifood2.cadastro.Restaurante;
import com.github.idelvan.ifood2.cadastro.infra.DTO;
import com.github.idelvan.ifood2.cadastro.infra.ValidDTO;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@ValidDTO
public class AdicionarRestauranteDTO implements DTO {

    @NotEmpty
    @NotNull
    public String proprietario;

    @Pattern(regexp = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}\\/[0-9]{4}\\-[0-9]{2}")
    @NotNull
    public String cnpj;

    @Size(min = 3, max = 30)
    public String nomeFantasia;

    public LocalizacaoDTO localizacao;

    @Override
    public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (Restaurante.find("cnpj", cnpj).count() > 0) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("CNPJ já cadastrado")
                    .addPropertyNode("cnpj")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }

}
