package com.controle.estoque.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.controle.estoque.dtos.request.CidadeRequest;
import com.controle.estoque.dtos.response.CidadeResponse;
import com.controle.estoque.entities.Cidade;
import com.controle.estoque.mappers.CidadeMapper;
import com.controle.estoque.repositories.CidadeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CidadeService {


  private final CidadeMapper cidadeMapper;
  private final CidadeRepository cidadeRepository;


  public List<CidadeResponse> listarCidades() {
    return cidadeRepository.findAll()
      .stream()
      .map(cidadeMapper::toCidadeResponse)
      .toList();
  }

  public CidadeResponse buscarCidadePorId(Long id) {
    return cidadeRepository.findById(id)
      .map(cidadeMapper::toCidadeResponse)
      .orElseThrow(() -> new RuntimeException("Cidade não encontrada"));
  }

  public CidadeResponse criarCidade(CidadeRequest request){

    Cidade cidade = cidadeMapper.toEntity(request);
    Cidade cidadeSalva = cidadeRepository.save(cidade);
    return cidadeMapper.toCidadeResponse(cidadeSalva);

  }

  public CidadeResponse atualizarCidade(Long id, CidadeRequest request) {

    Cidade cidade = cidadeRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Cidade não encontrada"));

      cidade.setNome(request.nome());
      cidade.setEstado(request.estado());
      cidade.setSede(request.sede());

    Cidade cidadeAtualizada = cidadeRepository.save(cidade);
    return cidadeMapper.toCidadeResponse(cidadeAtualizada);
  }

  public void deletarCidade(Long id) {
    Cidade cidade = cidadeRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Cidade não encontrada"));

    cidadeRepository.delete(cidade);
  }


}
