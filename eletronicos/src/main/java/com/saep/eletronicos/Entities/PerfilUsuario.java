package com.saep.eletronicos.Entities;

import org.springframework.security.core.GrantedAuthority;

public enum PerfilUsuario implements GrantedAuthority {

    ADMIN("ADMIN"),
    OPERADOR_ESTOQUE("OPERADOR_ESTOQUE"),
    VISUALIZADOR("VISUALIZADOR");

    private final String role;

    PerfilUsuario(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }
}