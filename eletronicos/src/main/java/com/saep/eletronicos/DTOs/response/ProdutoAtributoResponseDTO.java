package com.saep.eletronicos.DTOs.response;

import com.saep.eletronicos.Entities.UnidadeMedida;

public record ProdutoAtributoResponseDTO(

        String nomeAtributo,
        String valor,
        UnidadeMedida unidadeMedida

) {}
