package com.saep.eletronicos.DTOs.response;

public record AlertaEstoqueDTO(

        Long idProduto,
        String codigoSku,
        String nomeProduto,
        Integer quantidadeAtual,
        Integer estoqueMinimo,
        String mensagemAlerta

) {}