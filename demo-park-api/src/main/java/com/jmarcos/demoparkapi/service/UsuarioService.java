package com.jmarcos.demoparkapi.service;

import com.jmarcos.demoparkapi.entity.Usuario;
import com.jmarcos.demoparkapi.exception.UserNameUniqueViolationException;
import com.jmarcos.demoparkapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Transactional
     public Usuario salvar(Usuario usuario){

        try {
            return usuarioRepository.save(usuario);
        }catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new UserNameUniqueViolationException(String.format("Username {%s} já está cadastrado", usuario.getUsername()));
        }

     }
     @Transactional(readOnly = true)
     public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Usuário não encontrado")
        );
     }
     @Transactional
     public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha){
        if(!novaSenha.equals(confirmaSenha)){
            throw new RuntimeException("Nova senha não confere com confirmação de senha.");
        }
        Usuario user = buscarPorId(id);
        if (!user.getPassword().equals(senhaAtual)){
            throw new RuntimeException("Sua senha não confere.");
        }

        user.setPassword(novaSenha);
        return user;
     }
     @Transactional(readOnly = true)
     public List<Usuario> buscarTodos(){
        return usuarioRepository.findAll();
     }
}
