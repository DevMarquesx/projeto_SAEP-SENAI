package com.saep.eletronicos.Entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProdutoAtributoId implements Serializable {

    private Long idProduto;
    private Long idAtributo;


    public ProdutoAtributoId() {}

    public ProdutoAtributoId(Long idProduto, Long idAtributo) {
        this.idProduto = idProduto;
        this.idAtributo = idAtributo;
    }

    // Getters e Setters
    public Long getIdProduto() { return idProduto; }
    public void setIdProduto(Long idProduto) { this.idProduto = idProduto; }

    public Long getIdAtributo() { return idAtributo; }
    public void setIdAtributo(Long idAtributo) { this.idAtributo = idAtributo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoAtributoId that = (ProdutoAtributoId) o;
        return Objects.equals(idProduto, that.idProduto) && Objects.equals(idAtributo, that.idAtributo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto, idAtributo);
    }
}