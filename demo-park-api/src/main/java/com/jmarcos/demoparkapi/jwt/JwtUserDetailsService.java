package com.jmarcos.demoparkapi.jwt;

import com.jmarcos.demoparkapi.entity.Usuario;
import com.jmarcos.demoparkapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// UserDetailsService é uma classe do Spring Security que é usada para localizar um usuários no banco de dados
public class JwtUserDetailsService implements UserDetailsService {
    private final UsuarioService usuarioService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        return new JwtUserDetails(usuario); //recuperação do banco de dados

    }
    // Método para fazer a consulta pelo nome do usuário e retornar o token
    public JwtToken getTokenAuthenticated(String username){
        Usuario.Role role = usuarioService.buscarRolePorUsername(username);
        return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
    }
}
