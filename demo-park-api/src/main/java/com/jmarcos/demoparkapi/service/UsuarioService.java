package com.jmarcos.demoparkapi.service;

import com.jmarcos.demoparkapi.entity.Usuario;
import com.jmarcos.demoparkapi.exception.EntityNotFoundException;
import com.jmarcos.demoparkapi.exception.UserNameUniqueViolationException;
import com.jmarcos.demoparkapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
     public Usuario salvar(Usuario usuario){

        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario);
        }catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new UserNameUniqueViolationException(String.format("Username {%s} já está cadastrado", usuario.getUsername()));
        }

     }
    @Transactional(readOnly = true)
     public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id))
        );
     }
     @Transactional
     public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha){
        if(!novaSenha.equals(confirmaSenha)){
            throw new RuntimeException("Nova senha não confere com confirmação de senha.");
        }
        Usuario user = buscarPorId(id);
        if (!passwordEncoder.matches(senhaAtual, user.getPassword())){
            throw new RuntimeException("Sua senha não confere.");
        }

        user.setPassword(passwordEncoder.encode(novaSenha));
        return user;
     }
     @Transactional(readOnly = true)
     public List<Usuario> buscarTodos(){
        return usuarioRepository.findAll();
     }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Usuário com %s não encontrado ", username))
        );
    }
    @Transactional(readOnly = true)
    public Usuario.Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }
}
