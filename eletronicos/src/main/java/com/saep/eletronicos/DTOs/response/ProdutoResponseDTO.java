package com.saep.eletronicos.DTOs.response;

import java.math.BigDecimal;
import java.util.Set;

public record ProdutoResponseDTO(

        // Dados Básicos do Produto
        Long idProduto,
        String nome,
        String codigoSku,
        String fabricante,
        BigDecimal precoVenda,
        String nomeCategoria,

        // Dados do Estoque (1:1)
        Integer quantidadeAtual,
        Integer estoqueMinimo,

        // Especificações Técnicas (N:M)
        Set<ProdutoAtributoResponseDTO> atributos

) {}