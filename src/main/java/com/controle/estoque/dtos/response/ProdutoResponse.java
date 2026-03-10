package com.controle.estoque.dtos.response;

public record ProdutoResponse(
  Long id,
  String nome,
  String unidadeMedida,
  Boolean ativo) {}