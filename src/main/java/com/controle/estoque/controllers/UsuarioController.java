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

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
@PreAuthorize("hasRole('ADMIN')") 
public class UsuarioController {

  private final UsuarioService usuarioService;


  @GetMapping
  public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
    return ResponseEntity.ok(usuarioService.buscarUsuarios());
  }

  
  @GetMapping("/{id}")
  public ResponseEntity<UsuarioResponse> buscarUsuarioPorId(@PathVariable Long id) {
    return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
  }


  @PostMapping
  public ResponseEntity<UsuarioResponse> criarUsuario(@RequestBody UsuarioRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criarUsuario(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UsuarioResponse> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequest request) {
    return ResponseEntity.ok(usuarioService.atualizarUsuario(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> desativarUsuario(@PathVariable Long id) {
    usuarioService.desativarUsuario(id);
    return ResponseEntity.noContent().build();
  }



}
