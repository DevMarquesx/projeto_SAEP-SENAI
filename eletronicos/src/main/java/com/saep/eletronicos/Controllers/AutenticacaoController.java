package com.saep.eletronicos.Controllers;

import com.saep.eletronicos.DTOs.request.LoginRequestDTO;
import com.saep.eletronicos.DTOs.response.TokenResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        String perfil = authenticate.getAuthorities().iterator().next().getAuthority();
        return ResponseEntity.ok(new TokenResponseDTO("SIMULADO_JWT_TOKEN_PARA_USUARIO_LOGADO", perfil));
    }
}