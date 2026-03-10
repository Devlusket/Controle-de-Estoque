package com.controle.estoque.dtos.response;

import javax.management.relation.Role;

public record UsuarioResponse(
    Long id,
    String nome,
    String email,
    Role role,
    CidadeResponse cidade
) {}
