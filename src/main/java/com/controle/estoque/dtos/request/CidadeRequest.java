package com.controle.estoque.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CidadeRequest(
  @NotBlank String nome, 
  @NotBlank @Size(min = 2, max = 2) String estado,
  @NotBlank Boolean sede) {}
