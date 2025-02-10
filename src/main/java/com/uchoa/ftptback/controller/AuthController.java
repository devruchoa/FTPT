package com.uchoa.ftptback.controller;

import com.uchoa.ftptback.dto.UsuarioDTO;
import com.uchoa.ftptback.service.UsuarioService;
import com.uchoa.ftptback.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService usuarioService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsuarioDTO usuarioDTO) {
        usuarioService.salvarUsuario(usuarioDTO.getUsername(), usuarioDTO.getPassword());
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getUsername(), usuarioDTO.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(token);
    }
}
