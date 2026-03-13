package com.controle.estoque.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controle.estoque.dtos.request.MovimentacaoRequest;
import com.controle.estoque.dtos.response.MovimentacaoResponse;
import com.controle.estoque.services.MovimentacaoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movimentacoes")
public class MovimentacaoController {


  private final MovimentacaoService movimentacaoService;

  @GetMapping
  public ResponseEntity<List<MovimentacaoResponse>> listarMovimentacoes() {
    return ResponseEntity.ok(movimentacaoService.listarMovimentacoes());
  } 

  @GetMapping("/{id}")
  public ResponseEntity<MovimentacaoResponse> obterMovimentacaoPorId(@PathVariable Long id) {
    return ResponseEntity.ok(movimentacaoService.obterMovimentacaoPorId(id));
  }

  @GetMapping("/cidade/{cidadeId}")
  public ResponseEntity<List<MovimentacaoResponse>> listarMovimentacoesPorCidade(@PathVariable Long cidadeId) {
    return ResponseEntity.ok(movimentacaoService.listarMovimentacoesPorCidade(cidadeId));
  }

  @GetMapping("/produto/{produtoId}")
  public ResponseEntity<List<MovimentacaoResponse>> listarMovimentacoesPorProduto(@PathVariable Long produtoId) {
    return ResponseEntity.ok(movimentacaoService.listarMovimentacoesPorProduto(produtoId));
  }

  @PostMapping
  public ResponseEntity<MovimentacaoResponse> criarMovimentacao(@RequestBody MovimentacaoRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(movimentacaoService.registrarMovimentacao(request));
  }

}
