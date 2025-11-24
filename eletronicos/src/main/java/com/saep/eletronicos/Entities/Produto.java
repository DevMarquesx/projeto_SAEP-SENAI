package com.saep.eletronicos.Entities;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "produto")
@Audited
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String codigoSku;

    private String fabricante;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoVenda;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @OneToOne(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Estoque estoque;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProdutoAtributo> atributos;

    public Produto() {}

    public Produto(String nome, String codigoSku, String fabricante, BigDecimal precoVenda, Categoria categoria) {
        this.nome = nome;
        this.codigoSku = codigoSku;
        this.fabricante = fabricante;
        this.precoVenda = precoVenda;
        this.categoria = categoria;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCodigoSku() {
        return codigoSku;
    }

    public void setCodigoSku(String codigoSku) {
        this.codigoSku = codigoSku;
    }


    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }


    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    // Categoria (Relacionamento)
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public Set<ProdutoAtributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(Set<ProdutoAtributo> atributos) {
        this.atributos = atributos;
    }
}