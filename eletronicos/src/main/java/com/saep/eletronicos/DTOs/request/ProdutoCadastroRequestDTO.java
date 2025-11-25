package com.saep.eletronicos.DTOs.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

public class ProdutoCadastroRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres.")
    private String nome;

    @NotBlank(message = "O SKU é obrigatório.")
    public String codigoSku;

    private String fabricante;

    @NotNull(message = "O preço de venda é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    private BigDecimal precoVenda;

    @NotNull(message = "O ID da categoria é obrigatório.")
    private Long idCategoria;

    @NotNull(message = "A quantidade inicial é obrigatória.")
    @Min(value = 0, message = "A quantidade inicial não pode ser negativa.")
    private Integer quantidadeInicial;

    @NotNull(message = "O estoque mínimo é obrigatório.")
    @Min(value = 0, message = "O estoque mínimo não pode ser negativo.")
    private Integer estoqueMinimo;

    @NotEmpty(message = "O produto deve ter pelo menos um atributo específico.")
    private Set<ProdutoAtributoRequestDTO> atributos;

    public ProdutoCadastroRequestDTO() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCodigoSku() { return codigoSku; }
    public void setCodigoSku(String codigoSku) { this.codigoSku = codigoSku; }

    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    public BigDecimal getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(BigDecimal precoVenda) { this.precoVenda = precoVenda; }

    public Long getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }

    public Integer getQuantidadeInicial() { return quantidadeInicial; }
    public void setQuantidadeInicial(Integer quantidadeInicial) { this.quantidadeInicial = quantidadeInicial; }

    public Integer getEstoqueMinimo() { return estoqueMinimo; }
    public void setEstoqueMinimo(Integer estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }

    public Set<ProdutoAtributoRequestDTO> getAtributos() { return atributos; }
    public void setAtributos(Set<ProdutoAtributoRequestDTO> atributos) { this.atributos = atributos; }
}
