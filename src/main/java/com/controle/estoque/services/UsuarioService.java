package com.controle.estoque.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.controle.estoque.dtos.request.UsuarioRequest;
import com.controle.estoque.dtos.response.UsuarioResponse;
import com.controle.estoque.entities.Cidade;
import com.controle.estoque.entities.Usuario;
import com.controle.estoque.exceptions.ErrorMessages;
import com.controle.estoque.exceptions.ResourceNotFoundException;
import com.controle.estoque.mappers.UsuarioMapper;
import com.controle.estoque.repositories.CidadeRepository;
import com.controle.estoque.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {


  private final PasswordEncoder passwordEncoder;
  private final UsuarioRepository usuarioRepository;
  private final CidadeRepository cidadeRepository;
  private final UsuarioMapper usuarioMapper;




  public List<UsuarioResponse> buscarUsuarios() {
    return usuarioRepository.findAll()
        .stream()
        .map(usuarioMapper::toResponse)
        .toList();
  }

  public UsuarioResponse buscarUsuarioPorId(Long id) {
    return usuarioRepository.findById(id)
        .map(usuarioMapper::toResponse)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USUARIO_NAO_ENCONTRADO));
  }

  public void desativarUsuario(Long id) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USUARIO_NAO_ENCONTRADO));

    usuario.setAtivo(false);
    usuarioRepository.save(usuario);
  }

  public UsuarioResponse atualizarUsuario(Long id, UsuarioRequest request) {

    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USUARIO_NAO_ENCONTRADO));
    Cidade cidade = cidadeRepository.findById(request.cidadeId())
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CIDADE_NAO_ENCONTRADA));

        usuario.setEmail(request.email());
        usuario.setNome(request.nome());
        usuario.setRole(request.role());
        usuario.setCidade(cidade);

    Usuario usuarioAtualizado = usuarioRepository.save(usuario);
    return usuarioMapper.toResponse(usuarioAtualizado);
  }

  public UsuarioResponse criarUsuario(UsuarioRequest request) {

    Cidade cidade = cidadeRepository.findById(request.cidadeId())
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CIDADE_NAO_ENCONTRADA));

    Usuario usuario = usuarioMapper.toEntity(request);

    usuario.setCidade(cidade);
    usuario.setAtivo(true);
    usuario.setSenha(passwordEncoder.encode(request.senha()));


    Usuario usuarioSalvo = usuarioRepository.save(usuario);
    return usuarioMapper.toResponse(usuarioSalvo);
  }

}
