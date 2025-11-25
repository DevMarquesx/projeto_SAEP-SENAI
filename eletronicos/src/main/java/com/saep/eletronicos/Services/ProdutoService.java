package com.saep.eletronicos.Services;

import com.saep.eletronicos.DTOs.request.ProdutoCadastroRequestDTO;
import com.saep.eletronicos.DTOs.request.ProdutoAtributoRequestDTO;
import com.saep.eletronicos.DTOs.response.ProdutoResponseDTO;
import com.saep.eletronicos.DTOs.response.ProdutoAtributoResponseDTO;
import com.saep.eletronicos.Entities.*;
import com.saep.eletronicos.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private EstoqueRepository estoqueRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private AtributoEspecificoRepository atributoEspecificoRepository;
    @Autowired
    private ProdutoAtributoRepository produtoAtributoRepository;

    @Transactional
    public ProdutoResponseDTO cadastrar(ProdutoCadastroRequestDTO dto) {
        validarCadastro(dto);
        Produto produto = criarEntidadeProduto(dto);
        Estoque estoque = criarEntidadeEstoque(dto, produto);
        Set<ProdutoAtributo> atributosSalvos = criarEntidadesAtributos(dto, produto);
        Produto produtoSalvo = produtoRepository.save(produto);

        return toResponseDTO(produtoSalvo, estoque, atributosSalvos);
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado com o ID: " + id));

        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Estoque não encontrado para o Produto ID: " + id));

        Set<ProdutoAtributo> atributos = new HashSet<>(produtoAtributoRepository.findByIdIdProduto(id));

        return toResponseDTO(produto, estoque, atributos);
    }

    public List<ProdutoResponseDTO> listarOuBuscar(String termoBusca) {
        List<Produto> produtos;
        if (termoBusca == null || termoBusca.isBlank()) {
            produtos = produtoRepository.findAllByOrderByNomeAsc();
        } else {
            produtos = produtoRepository.findByNomeContainingIgnoreCaseOrderByNomeAsc(termoBusca);
        }
        return produtos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoCadastroRequestDTO dto) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado para atualização com o ID: " + id));
        validarAtualizacao(id, dto);

        produtoExistente.setNome(dto.getNome());
        produtoExistente.setCodigoSku(dto.getCodigoSku());
        produtoExistente.setFabricante(dto.getFabricante());
        produtoExistente.setPrecoVenda(dto.getPrecoVenda());

        Categoria categoriaRef = categoriaRepository.getReferenceById(dto.getIdCategoria());
        produtoExistente.setCategoria(categoriaRef);

        Estoque estoqueExistente = estoqueRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Estoque inconsistente para o Produto ID: " + id));
        estoqueExistente.setEstoqueMinimo(dto.getEstoqueMinimo());
        estoqueRepository.save(estoqueExistente);


        produtoAtributoRepository.deleteAll(produtoAtributoRepository.findByIdIdProduto(id));
        Set<ProdutoAtributo> atributosAtualizados = criarEntidadesAtributos(dto, produtoExistente);

        Produto produtoAtualizado = produtoRepository.save(produtoExistente);
        return toResponseDTO(produtoAtualizado, estoqueExistente, atributosAtualizados);
    }

    @Transactional
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new NoSuchElementException("Produto não encontrado para exclusão com o ID: " + id);
        }

        produtoAtributoRepository.deleteAll(produtoAtributoRepository.findByIdIdProduto(id));
        estoqueRepository.deleteById(id);
        produtoRepository.deleteById(id);
    }

    private void validarCadastro(ProdutoCadastroRequestDTO dto) {
        if (produtoRepository.findByCodigoSku(dto.getCodigoSku()) != null) {
            throw new IllegalArgumentException("O SKU '" + dto.getCodigoSku() + "' já está em uso por outro produto.");
        }
        validarRelacionamentos(dto);
    }

    private void validarAtualizacao(Long idAtual, ProdutoCadastroRequestDTO dto) {
        Produto produtoComMesmoSku = produtoRepository.findByCodigoSku(dto.getCodigoSku());

        if (produtoComMesmoSku != null && !produtoComMesmoSku.getIdProduto().equals(idAtual)) {
            throw new IllegalArgumentException("O SKU '" + dto.getCodigoSku() + "' já está em uso por outro produto.");
        }
        validarRelacionamentos(dto);
    }

    private void validarRelacionamentos(ProdutoCadastroRequestDTO dto) {
        categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new NoSuchElementException("Categoria não encontrada com o ID: " + dto.getIdCategoria()));
        for (ProdutoAtributoRequestDTO attrDto : dto.getAtributos()) {
            if (!atributoEspecificoRepository.existsById(attrDto.idAtributo())) {
                throw new NoSuchElementException("Atributo específico não encontrado com o ID: " + attrDto.idAtributo());
            }
        }
    }

    private Produto criarEntidadeProduto(ProdutoCadastroRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setCodigoSku(dto.getCodigoSku());
        produto.setFabricante(dto.getFabricante());
        produto.setPrecoVenda(dto.getPrecoVenda());

        Categoria categoriaRef = categoriaRepository.getReferenceById(dto.getIdCategoria());
        produto.setCategoria(categoriaRef);

        return produto;
    }

    private Estoque criarEntidadeEstoque(ProdutoCadastroRequestDTO dto, Produto produto) {
        Estoque estoque = new Estoque();
        estoque.setProduto(produto);
        estoque.setQuantidadeAtual(dto.getQuantidadeInicial());
        estoque.setEstoqueMinimo(dto.getEstoqueMinimo());
        return estoqueRepository.save(estoque);
    }

    private Set<ProdutoAtributo> criarEntidadesAtributos(ProdutoCadastroRequestDTO dto, Produto produto) {
        Set<ProdutoAtributo> atributosSalvos = new HashSet<>();

        for (ProdutoAtributoRequestDTO attrDto : dto.getAtributos()) {
            ProdutoAtributo produtoAtributo = new ProdutoAtributo();

            ProdutoAtributoId id = new ProdutoAtributoId();
            id.setIdProduto(produto.getIdProduto());
            id.setIdAtributo(attrDto.idAtributo());
            produtoAtributo.setId(id);

            produtoAtributo.setValorAtributo(attrDto.valor());

            AtributoEspecifico atributoRef = atributoEspecificoRepository.getReferenceById(attrDto.idAtributo());
            produtoAtributo.setProduto(produto);
            produtoAtributo.setAtributoEspecifico(atributoRef);

            atributosSalvos.add(produtoAtributoRepository.save(produtoAtributo));
        }
        return atributosSalvos;
    }
    private ProdutoResponseDTO toResponseDTO(Produto produto) {
        // 1. Busca o Estoque (o ID do Estoque é o mesmo ID do Produto)
        Estoque estoque = estoqueRepository.findById(produto.getIdProduto())
                .orElseThrow(() -> new IllegalStateException("Estoque inconsistente para o Produto ID: " + produto.getIdProduto()));

        // 2. Busca os Atributos relacionados
        Set<ProdutoAtributo> atributos = new HashSet<>(produtoAtributoRepository.findByIdIdProduto(produto.getIdProduto()));

        // 3. Chama o método toResponseDTO de três argumentos (que já existe)
        return toResponseDTO(produto, estoque, atributos);
    }

    // 🚨 MÉTODO DE CONVERSÃO USADO NO CADASTRO/ATUALIZAÇÃO (EXISTENTE) 🚨
    public ProdutoResponseDTO toResponseDTO(Produto produto, Estoque estoque, Set<ProdutoAtributo> produtoAtributos) {

        Set<ProdutoAtributoResponseDTO> atributosResponse = produtoAtributos.stream()
                .map(pa -> new ProdutoAtributoResponseDTO(
                        pa.getAtributoEspecifico().getNomeAtributo(),
                        pa.getValorAtributo(),
                        pa.getAtributoEspecifico().getUnidadeMedida()
                ))
                .collect(Collectors.toSet());

        return new ProdutoResponseDTO(
                produto.getIdProduto(),
                produto.getNome(),
                produto.getCodigoSku(),
                produto.getFabricante(),
                produto.getPrecoVenda(),
                produto.getCategoria().getNomeCategoria(),
                estoque.getQuantidadeAtual(),
                estoque.getEstoqueMinimo(),
                atributosResponse
        );
    }
}