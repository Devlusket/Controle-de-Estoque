package com.controle.estoque.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controle.estoque.dtos.request.ProdutoRequest;
import com.controle.estoque.dtos.response.ProdutoResponse;
import com.controle.estoque.services.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produto", description = "Endpoints para gerenciamento de produtos")
@RequiredArgsConstructor
public class ProdutoController {

  private final ProdutoService produtoService;


  @Operation(summary = "Listar produtos", description = "Retorna uma lista de todos os produtos cadastrados")
  @GetMapping
  public ResponseEntity<List<ProdutoResponse>> listarProdutos() {
    return ResponseEntity.ok(produtoService.listarProdutos());
  }

  @Operation(summary = "Buscar produto por ID", description = "Retorna os detalhes de um produto específico com base no ID fornecido")
  @GetMapping("/{id}")
  public ResponseEntity<ProdutoResponse> buscarProdutoPorId(@PathVariable Long id) {
    return ResponseEntity.ok(produtoService.buscarProdutoPorId(id));
  }
  
  @Operation(summary = "Atualizar produto", description = "Atualiza os detalhes de um produto existente com base no ID fornecido e nos dados enviados")
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<ProdutoResponse> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoRequest request) {
    return ResponseEntity.ok(produtoService.atualizarProduto(id, request));
  }

  @Operation(summary = "Desativar produto", description = "Desativa um produto existente com base no ID fornecido, tornando-o indisponível para movimentações futuras")
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> desativarProduto(@PathVariable Long id) {
    produtoService.desativarProduto(id);
    return ResponseEntity.noContent().build();
  }
  
  @Operation(summary = "Criar produto", description = "Cria um novo produto com base nos dados enviados")
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping()
  public ResponseEntity<ProdutoResponse> criarProduto(@RequestBody ProdutoRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.criarProduto(request));
  }


}
