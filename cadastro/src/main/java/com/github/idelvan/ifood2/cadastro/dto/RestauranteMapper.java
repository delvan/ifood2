package com.github.idelvan.ifood2.cadastro.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.github.idelvan.ifood2.cadastro.Restaurante;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

   @Mapping(target = "nome", source = "nomeFantasia")
   public Restaurante toRestaurante(AdicionarRestauranteDTO dto);
    
}
