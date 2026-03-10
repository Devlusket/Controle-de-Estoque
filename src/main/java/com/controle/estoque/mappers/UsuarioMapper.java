package com.controle.estoque.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.controle.estoque.dtos.request.UsuarioRequest;
import com.controle.estoque.dtos.response.UsuarioResponse;
import com.controle.estoque.entities.Usuario;

@Mapper(componentModel = "spring", uses = { CidadeMapper.class })
public interface UsuarioMapper {

  UsuarioResponse toResponse(Usuario usuario);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "senha", ignore = true)
  @Mapping(target = "ativo", constant = "true")
  @Mapping(target = "cidade", ignore = true)
  Usuario toEntity(UsuarioRequest usuarioRequest);

}
