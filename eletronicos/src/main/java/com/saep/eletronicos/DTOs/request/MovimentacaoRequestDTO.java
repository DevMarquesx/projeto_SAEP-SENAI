package com.saep.eletronicos.DTOs.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import com.saep.eletronicos.Entities.TipoMovimentacao;
import java.time.LocalDateTime;

public record MovimentacaoRequestDTO(

        @NotNull(message = "O ID do produto é obrigatório.")
        Long idProduto,

        @NotNull(message = "A quantidade é obrigatória.")
        @Min(value = 1, message = "A quantidade deve ser maior que zero.")
        Integer quantidade,

        @NotNull(message = "O tipo de movimentação é obrigatório (ENTRADA/SAIDA).")
        TipoMovimentacao tipo,

        // 🚨 CORREÇÃO DE SINTAXE: Adicionada a VÍRGULA aqui 🚨
        @NotNull(message = "O ID do usuário responsável é obrigatório.")
        Long idUsuarioResponsavel, // <-- VÍRGULA NECESSÁRIA

        // 🚨 CORREÇÃO DE SINTAXE: Anotação e campo na ordem correta 🚨
        @NotNull(message = "A Data e Hora da movimentação é obrigatória.")
        LocalDateTime dataHora
) {}