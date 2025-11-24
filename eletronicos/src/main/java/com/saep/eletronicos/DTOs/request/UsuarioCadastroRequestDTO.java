package com.saep.eletronicos.DTOs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioCadastroRequestDTO(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres.")
        String nome,

        @NotBlank(message = "O login (username) é obrigatório.")
        @Size(min = 4, max = 50, message = "O login deve ter entre 4 e 50 caracteres.")
        String login,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
        String senha,

        // ID do Perfil/Função (Ex: 1=ADMIN, 2=OPERADOR_ESTOQUE)
        @NotNull(message = "O ID do perfil é obrigatório.")
        Long idPerfil

) {}
