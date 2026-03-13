package com.controle.estoque.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.controle.estoque.dtos.request.ProdutoRequest;
import com.controle.estoque.dtos.response.ProdutoResponse;
import com.controle.estoque.entities.Produto;
import com.controle.estoque.exceptions.ErrorMessages;
import com.controle.estoque.exceptions.ResourceNotFoundException;
import com.controle.estoque.mappers.ProdutoMapper;
import com.controle.estoque.repositories.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {


  private final ProdutoMapper produtoMapper;
  private final ProdutoRepository produtoRepository;



  public List<ProdutoResponse> listarProdutos() {
    return produtoRepository.findByAtivoTrue()
      .stream()
      .map(produtoMapper::toProdutoResponse)
      .toList();
  }


  public ProdutoResponse buscarProdutoPorId(Long id) {
    return produtoRepository.findById(id)
      .map(produtoMapper::toProdutoResponse)
      .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUTO_NAO_ENCONTRADO));
  }

  public ProdutoResponse criarProduto(ProdutoRequest request) {

    Produto produto = produtoMapper.toEntity(request);
    Produto produtoSalvo = produtoRepository.save(produto);
    return produtoMapper.toProdutoResponse(produtoSalvo);

  }


  public ProdutoResponse atualizarProduto(Long id, ProdutoRequest request) {
    Produto produto = produtoRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUTO_NAO_ENCONTRADO));

    produto.setNome(request.nome());
    produto.setUnidadeMedida(request.unidadeMedida());

    Produto produtoAtualizado = produtoRepository.save(produto);
    return produtoMapper.toProdutoResponse(produtoAtualizado);
    
  }

  public void desativarProduto(Long id) {
    Produto produto = produtoRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUTO_NAO_ENCONTRADO));

    produto.setAtivo(false);
    produtoRepository.save(produto);
  }


}
