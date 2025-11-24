package com.saep.eletronicos.Repositories;

import com.saep.eletronicos.Entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Categoria findByNomeCategoriaIgnoreCase(String nomeCategoria);
}