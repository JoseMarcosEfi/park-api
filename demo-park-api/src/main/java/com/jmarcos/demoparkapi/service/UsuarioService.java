package com.jmarcos.demoparkapi.service;

import com.jmarcos.demoparkapi.entity.Usuario;
import com.jmarcos.demoparkapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PublicKey;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Transactional
     public Usuario salvar(Usuario usuario){
         return usuarioRepository.save(usuario);
     }
}
