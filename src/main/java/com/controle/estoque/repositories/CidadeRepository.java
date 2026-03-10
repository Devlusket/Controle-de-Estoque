package com.controle.estoque.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.controle.estoque.entities.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>{

  Optional<Cidade> findBySede(Boolean sede);

}
