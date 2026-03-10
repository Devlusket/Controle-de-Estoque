package com.controle.estoque.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.controle.estoque.dtos.request.ProdutoRequest;
import com.controle.estoque.dtos.response.ProdutoResponse;
import com.controle.estoque.entities.Produto;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

  ProdutoResponse toProdutoResponse(Produto produto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "ativo", ignore = true)
  Produto toEntity(ProdutoRequest produtoRequest);

}
