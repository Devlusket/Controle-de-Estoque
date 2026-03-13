package com.controle.estoque.services;

import org.springframework.stereotype.Service;

import com.controle.estoque.mappers.MovimentacaoMapper;
import com.controle.estoque.repositories.MovimentacaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {


  private final MovimentacaoRepository movimentacaoRepository;
  private final MovimentacaoMapper movimentacaoMapper;

  


}
