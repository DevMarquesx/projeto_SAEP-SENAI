package com.saep.eletronicos.DTOs.response;

import com.saep.eletronicos.Entities.TipoMovimentacao;
import java.time.LocalDateTime;

public record MovimentacaoResponseDTO(

        Long idMovimentacao,
        TipoMovimentacao tipo,
        Integer quantidade,
        LocalDateTime dataHora,

        // Detalhes para rastreabilidade
        Long idProduto,
        String nomeProduto,
        Long idUsuarioResponsavel,
        String nomeUsuarioResponsavel

) {}