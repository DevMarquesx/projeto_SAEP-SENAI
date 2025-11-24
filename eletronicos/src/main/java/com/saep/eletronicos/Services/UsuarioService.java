package com.saep.eletronicos.Services;

import com.saep.eletronicos.DTOs.request.UsuarioCadastroRequestDTO;
import com.saep.eletronicos.DTOs.response.UsuarioResponseDTO;
import com.saep.eletronicos.Entities.PerfilUsuario;
import com.saep.eletronicos.Entities.Usuario;
import com.saep.eletronicos.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO cadastrar(UsuarioCadastroRequestDTO dto) {

        if (usuarioRepository.findByLogin(dto.login()).isPresent()) {
            throw new IllegalArgumentException("O login '" + dto.login() + "' já está em uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setLogin(dto.login());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setPerfil(mapIdToPerfil(dto.idPerfil()));
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return toResponseDTO(usuarioSalvo);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com o ID: " + id));

        return toResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioCadastroRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado para atualização com o ID: " + id));

        if (!usuario.getLogin().equals(dto.login()) && usuarioRepository.findByLogin(dto.login()).isPresent()) {
            throw new IllegalArgumentException("O novo login '" + dto.login() + "' já está em uso por outro usuário.");
        }

        usuario.setNome(dto.nome());
        usuario.setLogin(dto.login());
        usuario.setPerfil(mapIdToPerfil(dto.idPerfil()));
        if (dto.senha() != null && !dto.senha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.senha()));
        }
        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return toResponseDTO(usuarioAtualizado);
    }
    @Transactional
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new NoSuchElementException("Usuário não encontrado para exclusão com o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getPerfil(),
                "ATIVO"
        );
    }

    private PerfilUsuario mapIdToPerfil(Long idPerfil) {
        return switch (idPerfil.intValue()) {
            case 1 -> PerfilUsuario.ADMIN;
            case 2 -> PerfilUsuario.OPERADOR_ESTOQUE;
            case 3 -> PerfilUsuario.VISUALIZADOR;
            default -> throw new NoSuchElementException("ID de Perfil inválido: " + idPerfil);
        };
    }
}