package com.controle.estoque.dtos.request;

import java.math.BigDecimal;

import com.controle.estoque.enums.TipoMovimentacao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovimentacaoRequest(
  String observacao,
  Long cidadeDestinoId,
  @NotNull TipoMovimentacao tipoMovimentacao,
  @NotNull @Positive BigDecimal quantidade,
  @NotNull Long produtoId
) {}
