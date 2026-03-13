package com.controle.estoque.services;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import com.controle.estoque.dtos.response.ProdutoResponse;
import com.controle.estoque.entities.Produto;
import com.controle.estoque.mappers.ProdutoMapper;
import com.controle.estoque.repositories.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
 class ProdutoServiceTest {

  @Mock
  private ProdutoRepository produtoRepository;

  @InjectMocks
  private ProdutoService produtoService;

  @Mock
  private ProdutoMapper produtoMapper;


  @Test
  void deveListarProdutosAtivos() {

    //prepara os dados mock
    Produto produto = Produto.builder()
        .id(1L)
        .nome("Arroz 1")
        .unidadeMedida("kg")
        .ativo(true)
        .build();

    ProdutoResponse response = new ProdutoResponse(1L, "Arroz 1", "kg", true);

    when(produtoRepository.findByAtivoTrue()).thenReturn(List.of(produto));
    when(produtoMapper.toProdutoResponse(any())).thenReturn(response);


    //executa o metodo

    List<ProdutoResponse> resultado = produtoService.listarProdutos();


    //verifica o resultado

    assertThat(resultado).hasSize(1);
    assertThat(resultado.get(0).nome()).isEqualTo("Arroz 1");

     

  }



  @Test
  void deveDesativarProduto(){

    Produto produto = Produto.builder()
        .id(1L)
        .nome("Arroz 1")
        .unidadeMedida("kg")
        .ativo(true)
        .build();

    when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));


    produtoService.desativarProduto(1L);

    assertThat(produto.getAtivo()).isFalse();

  }
}
