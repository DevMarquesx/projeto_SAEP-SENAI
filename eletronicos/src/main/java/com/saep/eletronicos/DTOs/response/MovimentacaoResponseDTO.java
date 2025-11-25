package com.saep.eletronicos.DTOs.response;

import java.time.LocalDateTime;

public record MovimentacaoResponseDTO(
        Long idMovimentacao,
        Long idProduto,
        String nomeProduto,
        String tipo, // String do Enum
        Integer quantidade,
        LocalDateTime dataHora,
        String nomeUsuarioResponsavel,
        Integer estoqueAtual, // novaQuantidade
        Boolean alertaEstoqueMinimo
) {}