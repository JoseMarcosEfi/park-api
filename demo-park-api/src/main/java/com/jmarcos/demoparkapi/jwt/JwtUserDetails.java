package com.jmarcos.demoparkapi.jwt;

import com.jmarcos.demoparkapi.entity.Usuario;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {

    private Usuario usuario;

    // GrantedAuthority é uma interface que representa uma autoridade concedida a um usuário
    public JwtUserDetails(Usuario usuario) {
        super(usuario.getUsername(), usuario.getPassword(), AuthorityUtils.createAuthorityList(usuario.getRole().name()));
        this.usuario = usuario;
    }
    // Construtor que recebe um usuário
    public Long getId(){
        return this.usuario.getId();
    }
    // Método que retorna o nome do usuário
    public String getRole(){
        return this.usuario.getRole().name();
    }
}
