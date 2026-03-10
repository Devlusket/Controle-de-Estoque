package com.controle.estoque.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record ProdutoRequest(
  @NotBlank String nome,
  @NotBlank String unidadeMedida) {}
