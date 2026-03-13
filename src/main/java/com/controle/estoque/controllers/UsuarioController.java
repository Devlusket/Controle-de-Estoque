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

import com.controle.estoque.dtos.request.UsuarioRequest;
import com.controle.estoque.dtos.response.UsuarioResponse;
import com.controle.estoque.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
@Tag(name = "Usuário", description = "Endpoints para gerenciamento de usuários")
@PreAuthorize("hasRole('ADMIN')") 
public class UsuarioController {

  private final UsuarioService usuarioService;

  @Operation(summary = "Listar usuários", description = "Retorna uma lista de todos os usuários cadastrados no sistema.")
  @GetMapping
  public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
    return ResponseEntity.ok(usuarioService.buscarUsuarios());
  }

  
  @Operation(summary = "Buscar usuário por ID", description = "Retorna os detalhes de um usuário específico com base no ID fornecido.")
  @GetMapping("/{id}")
  public ResponseEntity<UsuarioResponse> buscarUsuarioPorId(@PathVariable Long id) {
    return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
  }


  @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário no sistema com base nas informações fornecidas.")
  @PostMapping
  public ResponseEntity<UsuarioResponse> criarUsuario(@RequestBody UsuarioRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criarUsuario(request));
  }

  @Operation(summary = "Atualizar usuário", description = "Atualiza as informações de um usuário existente com base no ID fornecido.")
  @PutMapping("/{id}")
  public ResponseEntity<UsuarioResponse> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequest request) {
    return ResponseEntity.ok(usuarioService.atualizarUsuario(id, request));
  }

  @Operation(summary = "Desativar usuário", description = "Desativa um usuário específico com base no ID fornecido, tornando-o inativo no sistema.")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> desativarUsuario(@PathVariable Long id) {
    usuarioService.desativarUsuario(id);
    return ResponseEntity.noContent().build();
  }



}
