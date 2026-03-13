package com.controle.estoque.services;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.controle.estoque.dtos.request.LoginRequest;
import com.controle.estoque.dtos.response.LoginResponse;
import com.controle.estoque.entities.Usuario;
import com.controle.estoque.exceptions.ErrorMessages;
import com.controle.estoque.exceptions.ResourceNotFoundException;
import com.controle.estoque.repositories.UsuarioRepository;
import com.controle.estoque.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final UsuarioRepository usuarioRepository;
  private final JwtUtil jwtUtil;


  public LoginResponse login(LoginRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.email(), request.senha())
    );

    Usuario usuario = usuarioRepository.findByEmail(request.email())
      .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USUARIO_NAO_ENCONTRADO));


    String token = jwtUtil.generateToken(usuario);
    return new LoginResponse(token);



  }

}
