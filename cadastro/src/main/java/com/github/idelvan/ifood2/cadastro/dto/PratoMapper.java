package com.github.idelvan.ifood2.cadastro.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.github.idelvan.ifood2.cadastro.Prato;

@Mapper(componentModel = "cdi")
public interface PratoMapper {

    public Prato toPratoDTO(AdicionarPratoDTO dto);

}