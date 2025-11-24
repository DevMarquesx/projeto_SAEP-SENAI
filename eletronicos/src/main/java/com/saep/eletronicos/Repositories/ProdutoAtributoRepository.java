package com.saep.eletronicos.Repositories;

import com.saep.eletronicos.Entities.ProdutoAtributo;
import com.saep.eletronicos.Entities.ProdutoAtributoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProdutoAtributoRepository extends JpaRepository<ProdutoAtributo, ProdutoAtributoId> {

    List<ProdutoAtributo> findByIdIdProduto(Long idProduto);
}