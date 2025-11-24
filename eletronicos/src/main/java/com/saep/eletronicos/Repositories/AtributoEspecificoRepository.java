package com.saep.eletronicos.Repositories;

import com.saep.eletronicos.Entities.AtributoEspecifico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AtributoEspecificoRepository extends JpaRepository<AtributoEspecifico, Long> {

    Optional<AtributoEspecifico> findByNomeAtributoIgnoreCase(String nomeAtributo);
}