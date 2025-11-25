package com.saep.eletronicos.Services;

import com.saep.eletronicos.DTOs.request.MovimentacaoRequestDTO;
import com.saep.eletronicos.DTOs.response.MovimentacaoResponseDTO;
import com.saep.eletronicos.Entities.*;
import com.saep.eletronicos.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private EstoqueRepository estoqueRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public MovimentacaoResponseDTO registrarMovimentacao(MovimentacaoRequestDTO dto) {

        // 1. Recuperar e Validar Entidades
        Produto produto = produtoRepository.findById(dto.idProduto())
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado."));
        Estoque estoque = estoqueRepository.findById(dto.idProduto())
                .orElseThrow(() -> new IllegalStateException("Estoque inconsistente para o Produto."));
        Usuario usuarioResponsavel = usuarioRepository.findById(dto.idUsuarioResponsavel())
                .orElseThrow(() -> new NoSuchElementException("Usuário responsável não encontrado."));

        TipoMovimentacao tipo = dto.tipo();
        int quantidadeMovimentada = dto.quantidade();
        LocalDateTime dataMovimentacao = dto.dataHora();

        int novaQuantidade = estoque.getQuantidadeAtual();
        boolean alertaDisparado = false;

        // 2. Lógica de Estoque e Validação
        if (tipo == TipoMovimentacao.SAIDA) {
            if (estoque.getQuantidadeAtual() < quantidadeMovimentada) {
                throw new IllegalArgumentException("A quantidade de saída excede o estoque atual de " + estoque.getQuantidadeAtual());
            }
            novaQuantidade -= quantidadeMovimentada;
        } else if (tipo == TipoMovimentacao.ENTRADA) {
            novaQuantidade += quantidadeMovimentada;
        }

        // 3. Atualizar Estoque
        estoque.setQuantidadeAtual(novaQuantidade);
        estoqueRepository.save(estoque);

        // 4. Registrar Movimentação
        Movimentacao movimentacao = new Movimentacao();

        movimentacao.setProduto(produto);
        movimentacao.setUsuarioResponsavel(usuarioResponsavel);
        movimentacao.setTipo(tipo);
        movimentacao.setQuantidade(quantidadeMovimentada);
        movimentacao.setDataHora(dataMovimentacao);

        Movimentacao movimentacaoSalva = movimentacaoRepository.save(movimentacao);

        // 5. Verificar Alerta
        if (tipo == TipoMovimentacao.SAIDA && novaQuantidade < estoque.getEstoqueMinimo()) {
            alertaDisparado = true;
        }

        // 6. Mapear e Retornar
        return toResponseDTO(movimentacaoSalva, novaQuantidade, alertaDisparado);
    }

    private MovimentacaoResponseDTO toResponseDTO(Movimentacao movimentacao, Integer novaQuantidade, boolean alertaEstoqueMinimo) {
        return new MovimentacaoResponseDTO(
                movimentacao.getIdMovimentacao(),
                movimentacao.getProduto().getIdProduto(),
                movimentacao.getProduto().getNome(),
                movimentacao.getTipo().toString(),
                movimentacao.getQuantidade(),
                movimentacao.getDataHora(),
                movimentacao.getUsuarioResponsavel().getNome(),
                novaQuantidade,
                alertaEstoqueMinimo
        );
    }
}