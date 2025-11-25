package com.saep.eletronicos.Controllers;

import com.saep.eletronicos.Services.ProdutoService;
import com.saep.eletronicos.DTOs.request.ProdutoCadastroRequestDTO;
import com.saep.eletronicos.DTOs.response.ProdutoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OPERADOR_ESTOQUE')")
    public ResponseEntity<ProdutoResponseDTO> cadastrar(@RequestBody @Valid ProdutoCadastroRequestDTO dto) {
        ProdutoResponseDTO novoProduto = produtoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OPERADOR_ESTOQUE', 'VISUALIZADOR')")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        ProdutoResponseDTO produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OPERADOR_ESTOQUE', 'VISUALIZADOR')")
    public ResponseEntity<List<ProdutoResponseDTO>> listarOuBuscar(@RequestParam(required = false) String termoBusca) {
        List<ProdutoResponseDTO> produtos = produtoService.listarOuBuscar(termoBusca);
        return ResponseEntity.ok(produtos);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OPERADOR_ESTOQUE')")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid ProdutoCadastroRequestDTO dto) {
        ProdutoResponseDTO produtoAtualizado = produtoService.atualizar(id, dto);
        return ResponseEntity.ok(produtoAtualizado);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}