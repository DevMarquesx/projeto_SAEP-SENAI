package com.saep.eletronicos.Entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "atributo_especifico")
public class AtributoEspecifico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAtributo;

    @Column(nullable = false, unique = true)
    private String nomeAtributo; // Ex: Memória RAM, Resolução

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnidadeMedida unidadeMedida; // Ex: GB, POLEGADAS

    // 🔗 Relacionamento Inverso: Um Atributo é usado em Muitos Registros de ProdutoAtributo
    @OneToMany(mappedBy = "atributoEspecifico", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProdutoAtributo> produtosAtributos;

    // --- Construtores, Getters e Setters ---

    public AtributoEspecifico() {}

    public AtributoEspecifico(String nomeAtributo, UnidadeMedida unidadeMedida) {
        this.nomeAtributo = nomeAtributo;
        this.unidadeMedida = unidadeMedida;
    }

    // Getters e Setters
    public Long getIdAtributo() { return idAtributo; }
    public void setIdAtributo(Long idAtributo) { this.idAtributo = idAtributo; }

    public String getNomeAtributo() { return nomeAtributo; }
    public void setNomeAtributo(String nomeAtributo) { this.nomeAtributo = nomeAtributo; }

    public UnidadeMedida getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(UnidadeMedida unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public Set<ProdutoAtributo> getProdutosAtributos() { return produtosAtributos; }
    public void setProdutosAtributos(Set<ProdutoAtributo> produtosAtributos) { this.produtosAtributos = produtosAtributos; }
}