package com.controle.estoque.dtos.response;

public record CidadeResponse(
  Long id,
  String nome,
  String estado,
  Boolean sede) {}