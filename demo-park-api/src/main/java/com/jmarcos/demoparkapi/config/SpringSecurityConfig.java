package com.jmarcos.demoparkapi.config;

import com.jmarcos.demoparkapi.jwt.JwtAuthenticationEntryPoint;
import com.jmarcos.demoparkapi.jwt.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@EnableMethodSecurity // habilita a segurança baseada em anotações
@EnableWebMvc
@Configuration //indica que a classe é uma classe de configuração
public class SpringSecurityConfig {

    //liberação do swagger
    private static final String[] DOCUMENTATION_OPENAPI = {
            "/docs/index.html",
            "/docs-estacionamento.html", "/docs-estacionamento/**",
            "/v3/api-docs/**",
            "/swagger-ui-custom.html", "/swagger-ui.html", "/swagger-ui/**",
            "/**.html", "/webjars/**", "/configuration/**", "/swagger-resources/**"
    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable) // desabilita o csrf
                .formLogin(AbstractHttpConfigurer::disable) // desabilita o formulário de login
                .httpBasic(AbstractHttpConfigurer::disable) // desabilita o http basic authenticator
                .authorizeHttpRequests(auth -> auth // configura as autorizações
                        .requestMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll() // permite o acesso ao endpoint de cadastro de usuários
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()// permite o acesso a rota de autenticação para qualquer um
                        .requestMatchers(
                                antMatcher(HttpMethod.POST, "/api/v1/usuarios"),
                                antMatcher(HttpMethod.POST, "/api/v1/auth"),
                                antMatcher("/docs-park.html"),
                                antMatcher("/docs-park/**"),
                                antMatcher("/swagger-ui.html"),
                                antMatcher("/swagger-ui/**"),
                                antMatcher("/webjars/**")
                        ).permitAll()
                        .anyRequest().authenticated()// qualquer outra requisição precisa de autenticação
                ).sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // configura a política de sessão para stateless
                ).addFilterBefore(
                        jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class// adiciona o filtro de autorização
                ).exceptionHandling(ex -> ex.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                ).build();
    }

    // Esse método retorna uma instância de JwtAuthorizationFilter
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter();
    }

    // Esse método criptografa a senha do usuário no banco de dados
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder(); // metodo que criptografa a senha
    }

    // Esse método retorna uma instância de AuthenticationManage
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
