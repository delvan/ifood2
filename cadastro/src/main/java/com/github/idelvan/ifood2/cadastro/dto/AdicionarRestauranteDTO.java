package com.github.idelvan.ifood2.cadastro.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AdicionarRestauranteDTO {

    @NotEmpty
    @NotNull
    public String proprietario;

    public String cnpj;

    @Size(min = 3, max = 30)
    public String nomeFantasia;

    public LocalizacaoDTO localizacao;
    
}
