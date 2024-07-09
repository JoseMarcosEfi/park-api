package com.jmarcos.demoparkapi.repository;

import com.jmarcos.demoparkapi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    @Query("SELECT u.role from Usuario u where u.username like :username")
    Usuario.Role findRoleByUsername(String username);
}
