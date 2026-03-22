package com.controle.estoque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.controle.estoque.entities.Movimentacao;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

  List<Movimentacao> findByProdutoId(Long produtoId);

  List<Movimentacao> findByCidadeOrigemId(Long cidadeOrigemId);

  List<Movimentacao> findByCidadeDestinoId(Long cidadeDestinoId);

  List<Movimentacao> findByCidadeOrigemEstado(String estado);

  List<Movimentacao> findAllByOrderByDataMovimentacaoDesc();

  List<Movimentacao> findByCidadeOrigemIdOrderByDataMovimentacaoDesc(Long cidadeOrigemId);

}
