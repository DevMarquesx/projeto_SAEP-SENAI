package com.saep.eletronicos.Entities;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode; // 👈 NOVA IMPORTAÇÃO

@Entity
@Table(name = "produto_atributo")
@Audited
public class ProdutoAtributo {


    @EmbeddedId
    private ProdutoAtributoId id;

    @MapsId("idProduto")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto")
    private Produto produto;

    // 🚨 CORREÇÃO APLICADA AQUI:
    // Informa ao Envers para não auditar a entidade de destino (AtributoEspecifico)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @MapsId("idAtributo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_atributo")
    private AtributoEspecifico atributoEspecifico;

    @Column(nullable = false, length = 100)
    private String valorAtributo;

    // --- Construtores, Getters e Setters ---

    public ProdutoAtributo() {
        this.id = new ProdutoAtributoId();
    }

    public ProdutoAtributo(Produto produto, AtributoEspecifico atributoEspecifico, String valorAtributo) {
        this.produto = produto;
        this.atributoEspecifico = atributoEspecifico;
        this.valorAtributo = valorAtributo;
        // Inicializa a chave composta usando as chaves das entidades relacionadas
        this.id = new ProdutoAtributoId(produto.getIdProduto(), atributoEspecifico.getIdAtributo());
    }

    // Getters e Setters
    public ProdutoAtributoId getId() { return id; }
    public void setId(ProdutoAtributoId id) { this.id = id; }

    public String getValorAtributo() { return valorAtributo; }
    public void setValorAtributo(String valorAtributo) { this.valorAtributo = valorAtributo; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public AtributoEspecifico getAtributoEspecifico() { return atributoEspecifico; }
    public void setAtributoEspecifico(AtributoEspecifico atributoEspecifico) { this.atributoEspecifico = atributoEspecifico; }
}