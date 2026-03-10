package com.controle.estoque.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.controle.estoque.dtos.request.MovimentacaoRequest;
import com.controle.estoque.dtos.response.MovimentacaoResponse;
import com.controle.estoque.entities.Movimentacao;

@Mapper(componentModel = "spring", uses = { UsuarioMapper.class, ProdutoMapper.class, CidadeMapper.class })
public interface MovimentacaoMapper {

  MovimentacaoResponse toResponse(Movimentacao movimentacao);


  @Mapping(target = "id", ignore = true)
  @Mapping(target = "dataMovimentacao", ignore = true)
  @Mapping(target = "usuario", ignore = true)
  @Mapping(target = "produto", ignore = true)
  @Mapping(target = "cidadeOrigem", ignore = true)
  @Mapping(target = "cidadeDestino", ignore = true)
  Movimentacao toEntity(MovimentacaoRequest movimentacaoRequest);

}
