package com.uchoa.ftptback.service;

import com.uchoa.ftptback.model.Usuario;
import com.uchoa.ftptback.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario salvarUsuario(String username, String senha) {
        if (usuarioRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Usuário já existe!");
        }
        Usuario usuario = new Usuario(username, passwordEncoder.encode(senha));
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
