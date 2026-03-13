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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movimentacoes")
@Tag(name = "Movimentações", description = "Endpoints para gerenciamento de movimentações de estoque")
public class MovimentacaoController {


  private final MovimentacaoService movimentacaoService;

  @Operation(summary = "Listar todas as movimentações", description = "Retorna uma lista de todas as movimentações de estoque registradas")
  @GetMapping
  public ResponseEntity<List<MovimentacaoResponse>> listarMovimentacoes() {
    return ResponseEntity.ok(movimentacaoService.listarMovimentacoes());
  } 

  @Operation(summary = "Buscar movimentação por ID", description = "Retorna os detalhes de uma movimentação específica com base no ID fornecido")
  @GetMapping("/{id}")
  public ResponseEntity<MovimentacaoResponse> obterMovimentacaoPorId(@PathVariable Long id) {
    return ResponseEntity.ok(movimentacaoService.obterMovimentacaoPorId(id));
  }

  @Operation(summary = "Listar movimentações por cidade", description = "Retorna uma lista de movimentações de estoque para uma cidade específica")
  @GetMapping("/cidade/{cidadeId}")
  public ResponseEntity<List<MovimentacaoResponse>> listarMovimentacoesPorCidade(@PathVariable Long cidadeId) {
    return ResponseEntity.ok(movimentacaoService.listarMovimentacoesPorCidade(cidadeId));
  }

  @Operation(summary = "Listar movimentações por produto", description = "Retorna uma lista de movimentações de estoque para um produto específico")
  @GetMapping("/produto/{produtoId}")
  public ResponseEntity<List<MovimentacaoResponse>> listarMovimentacoesPorProduto(@PathVariable Long produtoId) {
    return ResponseEntity.ok(movimentacaoService.listarMovimentacoesPorProduto(produtoId));
  }

  @Operation(summary = "Criar nova movimentação", description = "Registra uma nova movimentação de estoque com base nos dados fornecidos")
  @PostMapping
  public ResponseEntity<MovimentacaoResponse> criarMovimentacao(@RequestBody MovimentacaoRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(movimentacaoService.registrarMovimentacao(request));
  }

}
