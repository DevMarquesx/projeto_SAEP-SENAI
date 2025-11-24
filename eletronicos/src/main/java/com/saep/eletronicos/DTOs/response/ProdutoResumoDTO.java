package com.saep.eletronicos.DTOs.response;

import java.math.BigDecimal;

public record ProdutoResumoDTO(

        Long idProduto,
        String nome,
        String codigoSku,
        BigDecimal precoVenda,
        String nomeCategoria,
        Integer quantidadeAtual

) {}
