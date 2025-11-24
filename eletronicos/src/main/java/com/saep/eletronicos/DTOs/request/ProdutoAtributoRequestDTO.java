package com.saep.eletronicos.DTOs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoAtributoRequestDTO(

        @NotNull(message = "O ID do atributo não pode ser nulo.")
        Long idAtributo,

        @NotBlank(message = "O valor do atributo é obrigatório.")
        String valor

) {}
