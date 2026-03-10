package com.controle.estoque.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.controle.estoque.enums.TipoMovimentacao;


public record MovimentacaoResponse(
  Long id,
  TipoMovimentacao tipoMovimentacao,
  BigDecimal quantidade,
  String observacao,
  LocalDateTime dataMovimentacao,
  ProdutoResponse produto,
  UsuarioResponse usuario,
  CidadeResponse cidadeDestino,
  CidadeResponse cidadeOrigem
) {}
