package com.saep.eletronicos.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "estoque")
public class Estoque {

    // 🔑 Chave Primária Compartilhada (PK e FK ao mesmo tempo)
    @Id
    private Long idProduto;

    // --- Relacionamento (Lado N:1 do 1:1) ---
    @OneToOne
    @MapsId // Usa o idProduto como Primary Key E Foreign Key
    @JoinColumn(name = "id_produto")
    private Produto produto;

    // --- Atributos de Inventário ---
    @Column(nullable = false)
    private Integer quantidadeAtual;

    // Crucial para os alertas individuais
    @Column(nullable = false)
    private Integer estoqueMinimo;

    // --- Construtores, Getters e Setters (Resumidos) ---

    public Estoque() {}

    public Long getIdProduto() { return idProduto; }
    public void setIdProduto(Long idProduto) { this.idProduto = idProduto; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public Integer getQuantidadeAtual() { return quantidadeAtual; }
    public void setQuantidadeAtual(Integer quantidadeAtual) { this.quantidadeAtual = quantidadeAtual; }

    public Integer getEstoqueMinimo() { return estoqueMinimo; }
    public void setEstoqueMinimo(Integer estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }
}
