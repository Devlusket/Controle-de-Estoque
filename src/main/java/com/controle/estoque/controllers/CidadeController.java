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

import com.controle.estoque.dtos.request.CidadeRequest;
import com.controle.estoque.dtos.response.CidadeResponse;
import com.controle.estoque.services.CidadeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cidades")
@RequiredArgsConstructor
public class CidadeController {


  private final CidadeService cidadeService;

  @GetMapping
  public ResponseEntity<List<CidadeResponse>> listarCidades() {
    return ResponseEntity.ok(cidadeService.listarCidades());
  }


  @GetMapping("/{id}")
  public ResponseEntity<CidadeResponse> buscarCidadePorId(@PathVariable Long id) {
    return ResponseEntity.ok(cidadeService.buscarCidadePorId(id));
  }


  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<CidadeResponse> criarCidade(@RequestBody CidadeRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(cidadeService.criarCidade(request));
  }


  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<CidadeResponse> atualizarCidade(@PathVariable Long id, @RequestBody CidadeRequest request) {
    return ResponseEntity.ok(cidadeService.atualizarCidade(id, request));
  }


  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarCidade(@PathVariable Long id) {
    cidadeService.deletarCidade(id);
    return ResponseEntity.noContent().build();
  }


}
