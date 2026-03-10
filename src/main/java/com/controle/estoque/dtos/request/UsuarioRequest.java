package com.controle.estoque.dtos.request;

import com.controle.estoque.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequest(
    @NotBlank    String nome,
    @NotBlank  @Email  String email,
    @NotBlank    String senha,
    @NotNull Role role,
    Long cidadeId
) {}
