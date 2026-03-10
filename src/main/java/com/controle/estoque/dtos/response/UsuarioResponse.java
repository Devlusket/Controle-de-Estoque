package com.controle.estoque.dtos.response;

import com.controle.estoque.enums.Role;

public record UsuarioResponse(
    Long id,
    String nome,
    String email,
    Role role,
    CidadeResponse cidade
) {}
