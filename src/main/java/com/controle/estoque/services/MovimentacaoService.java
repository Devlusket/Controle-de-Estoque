package com.controle.estoque.services;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.controle.estoque.dtos.request.MovimentacaoRequest;
import com.controle.estoque.dtos.response.MovimentacaoResponse;
import com.controle.estoque.entities.Cidade;
import com.controle.estoque.entities.Movimentacao;
import com.controle.estoque.entities.Produto;
import com.controle.estoque.entities.Usuario;
import com.controle.estoque.enums.Role;
import com.controle.estoque.exceptions.ErrorMessages;
import com.controle.estoque.exceptions.ResourceNotFoundException;
import com.controle.estoque.mappers.MovimentacaoMapper;
import com.controle.estoque.repositories.CidadeRepository;
import com.controle.estoque.repositories.MovimentacaoRepository;
import com.controle.estoque.repositories.ProdutoRepository;
import com.controle.estoque.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {


  private final UsuarioRepository usuarioRepository;
  private final ProdutoRepository produtoRepository;
  private final CidadeRepository cidadeRepository;
  private final MovimentacaoRepository movimentacaoRepository;
  private final MovimentacaoMapper movimentacaoMapper;


  private Usuario getUsuarioAutenticado() {
    String email = SecurityContextHolder.getContext()
        .getAuthentication()
        .getName();

    return usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USUARIO_NAO_ENCONTRADO));
  }

  public List<MovimentacaoResponse> listarMovimentacoes() {

    Usuario usuario = getUsuarioAutenticado();

    if (usuario.getRole() == Role.ADMIN) {
      return movimentacaoRepository.findAllByOrderByDataMovimentacaoDesc().stream()
          .map(movimentacaoMapper::toResponse)
          .toList();
    } else {

      return movimentacaoRepository.findByCidadeOrigemIdOrderByDataMovimentacaoDesc(usuario.getCidade().getId())
          .stream()
          .map(movimentacaoMapper::toResponse)
          .toList();
    }
    
  }


  public List<MovimentacaoResponse> listarMovimentacoesPorProduto(Long produtoId) {

    Usuario usuario = getUsuarioAutenticado();

    if (usuario.getRole() == Role.ADMIN) {
      return movimentacaoRepository.findByProdutoId(produtoId)
          .stream()
          .map(movimentacaoMapper::toResponse)
          .toList();
    } else {
      return movimentacaoRepository.findByProdutoId(produtoId)
          .stream()
          .filter(m -> m.getCidadeOrigem() != null && m.getCidadeOrigem().getId().equals(usuario.getCidade().getId()))
          .map(movimentacaoMapper::toResponse)
          .toList();
    }
    
  }

  public List<MovimentacaoResponse> listarMovimentacoesPorCidade(Long cidadeId) {

    Usuario usuario = getUsuarioAutenticado();

    if (usuario.getRole() == Role.ADMIN) {
      return movimentacaoRepository.findByCidadeOrigemId(cidadeId)
          .stream()
          .map(movimentacaoMapper::toResponse)
          .toList();
    } else {
      Long cidadeDoCliente = usuario.getCidade().getId();
        return movimentacaoRepository.findByCidadeOrigemId(cidadeDoCliente)
            .stream()
            .map(movimentacaoMapper::toResponse)
            .toList();
    }
    
  }


  
  public MovimentacaoResponse obterMovimentacaoPorId(Long id) {
    return movimentacaoRepository.findById(id)
        .map(movimentacaoMapper::toResponse)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.MOVIMENTACAO_NAO_ENCONTRADA));
  }



  public MovimentacaoResponse registrarMovimentacao(MovimentacaoRequest request) {


    Usuario usuario = getUsuarioAutenticado();

    Produto produto = produtoRepository.findById(request.produtoId())
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUTO_NAO_ENCONTRADO)); 
    

    Movimentacao movimentacao = new Movimentacao();
    movimentacao.setUsuario(usuario);
    movimentacao.setProduto(produto);
    movimentacao.setQuantidade(request.quantidade());
    movimentacao.setObservacao(request.observacao());
    movimentacao.setTipoMovimentacao(request.tipoMovimentacao());

    switch (request.tipoMovimentacao()) {

      case ENTRADA -> {
        movimentacao.setCidadeOrigem(null);
        movimentacao.setCidadeDestino(usuario.getCidade());
      }
      case SAIDA -> {
        movimentacao.setCidadeOrigem(usuario.getCidade());
        movimentacao.setCidadeDestino(null);
      }
      case TRANSFERENCIA -> {
        movimentacao.setCidadeOrigem(usuario.getCidade());
        if (usuario.getRole() == Role.ADMIN) {
          Cidade cidadeDestino = cidadeRepository.findById(request.cidadeDestinoId())
              .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CIDADE_NAO_ENCONTRADA));
          movimentacao.setCidadeDestino(cidadeDestino);
        } else {
        Cidade sede = cidadeRepository.findBySede(true)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CIDADE_NAO_ENCONTRADA));
        movimentacao.setCidadeDestino(sede);
        }

      }

    }

    Movimentacao movimentacaoSalva = movimentacaoRepository.save(movimentacao);
    return movimentacaoMapper.toResponse(movimentacaoSalva);


  }
}
