package com.saep.eletronicos.DTOs.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import com.saep.eletronicos.Entities.TipoMovimentacao;

public record MovimentacaoRequestDTO(

        @NotNull(message = "O ID do produto é obrigatório.")
        Long idProduto,

        @NotNull(message = "A quantidade é obrigatória.")
        @Min(value = 1, message = "A quantidade deve ser maior que zero.")
        Integer quantidade,

        @NotNull(message = "O tipo de movimentação é obrigatório (ENTRADA/SAIDA).")
        TipoMovimentacao tipo

) {}