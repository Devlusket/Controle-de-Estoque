package com.controle.estoque.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.controle.estoque.dtos.request.CidadeRequest;
import com.controle.estoque.dtos.response.CidadeResponse;
import com.controle.estoque.entities.Cidade;

@Mapper(componentModel = "spring")
public interface CidadeMapper {

  CidadeResponse toCidadeResponse(Cidade cidade);

  @Mapping(target = "id", ignore = true)
  Cidade toEntity(CidadeRequest cidadeRequest);


}
