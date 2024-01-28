package com.github.idelvan.ifood2.cadastro.dto;

import jakarta.validation.constraints.Size;

public class RestauranteDTO {

    public String proprietario;

    public String cnpj;

    @Size(min = 3, max = 30)
    public String nomeFantasia;

    public String dataCriacao;

    public LocalizacaoDTO localizacao;
}
