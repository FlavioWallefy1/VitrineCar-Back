package br.com.ifpe.demo.controller;

import br.com.ifpe.demo.model.Usuario;
import br.com.ifpe.demo.repository.UsuarioRepository;
import br.com.ifpe.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/logado")
    public Usuario getUsuarioAutenticado(Authentication authentication) {
        String email = authentication.getName(); // pega o email do token
        return usuarioRepository.findByEmail(email).orElse(null);
    }


    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        usuario.setRole("USER");
        return usuarioService.criarUsuario(usuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin-criar")
    public ResponseEntity<Usuario> criarAdmin(@RequestBody Usuario usuario) {
        usuario.setRole("ADMIN");
        Usuario criado = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/{id}")
    public Usuario buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @PutMapping("/{id}")
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        return usuarioService.atualizarUsuario(id, usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.removerUsuario(id);
    }
}
