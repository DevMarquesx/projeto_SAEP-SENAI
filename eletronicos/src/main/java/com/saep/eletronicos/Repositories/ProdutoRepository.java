package com.saep.eletronicos.Repositories;

import com.saep.eletronicos.Entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
    List<Produto> findAllByOrderByNomeAsc();
    Produto findByCodigoSku(String codigoSku);
}