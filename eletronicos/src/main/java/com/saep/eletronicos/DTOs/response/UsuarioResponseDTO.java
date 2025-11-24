package com.saep.eletronicos.DTOs.response;

import com.saep.eletronicos.Entities.PerfilUsuario;

public record UsuarioResponseDTO(

        Long idUsuario,
        String nome,
        String login,
        PerfilUsuario perfil,
        String status

) {}
