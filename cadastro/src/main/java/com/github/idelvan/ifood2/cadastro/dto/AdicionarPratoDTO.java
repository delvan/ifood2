package com.github.idelvan.ifood2.cadastro.dto;

import java.math.BigDecimal;

import com.github.idelvan.ifood2.cadastro.infra.DTO;

public class AdicionarPratoDTO implements DTO {

    public String nome;

    public String descricao;

    public RestauranteDTO restaurante;

    public BigDecimal preco;

 

}
