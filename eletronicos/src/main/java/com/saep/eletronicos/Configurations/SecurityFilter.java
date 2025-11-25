package com.saep.eletronicos.Configurations;

import com.saep.eletronicos.Repositories.UsuarioRepository;
import com.saep.eletronicos.Services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Removemos @Component, pois ele será criado como Bean no SecurityConfiguration
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository repository;

    // 🚨 Construtor para injeção de dependências, usado pelo Bean em SecurityConfiguration
    public SecurityFilter(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = recuperarToken(request);

        // Somente processa o token se ele existir
        if (tokenJWT != null) {
            String login = tokenService.getSubject(tokenJWT);

            // Busca o usuário no banco de dados usando o login extraído do token
            UserDetails usuario = repository.findByLogin(login).orElse(null);

            // Se o usuário existir e for válido, define a autenticação no SecurityContextHolder
            if (usuario != null) {
                // Cria o objeto Authentication: (principal, credenciais, authorities)
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                // Define o usuário como autenticado para a requisição atual
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continua o fluxo da requisição
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}