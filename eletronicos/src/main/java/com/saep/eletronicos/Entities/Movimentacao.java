package com.saep.eletronicos.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacao")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimentacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private LocalDateTime dataHora; // A data será setada pelo service a partir do DTO

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_responsavel", nullable = false)
    private Usuario usuarioResponsavel;

    // Construtor Padrão (necessário para JPA)
    public Movimentacao() {}

    // 🚨 Construtor usado pelo Service para criar a entidade com a data fornecida
    public Movimentacao(TipoMovimentacao tipo, Integer quantidade, Produto produto, Usuario usuarioResponsavel, LocalDateTime dataHora) {
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.produto = produto;
        this.usuarioResponsavel = usuarioResponsavel;
        this.dataHora = dataHora; // Usa a data fornecida
    }

    // --- Getters e Setters (Demais campos omitidos por brevidade) ---
    public Long getIdMovimentacao() { return idMovimentacao; }
    public void setIdMovimentacao(Long idMovimentacao) { this.idMovimentacao = idMovimentacao; }
    // ... (demais getters e setters)
    public TipoMovimentacao getTipo() { return tipo; }
    public void setTipo(TipoMovimentacao tipo) { this.tipo = tipo; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    public Usuario getUsuarioResponsavel() { return usuarioResponsavel; }
    public void setUsuarioResponsavel(Usuario usuarioResponsavel) { this.usuarioResponsavel = usuarioResponsavel; }
}