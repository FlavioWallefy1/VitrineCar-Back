package br.com.ifpe.demo.controller;

import br.com.ifpe.demo.model.Usuario;
import br.com.ifpe.demo.model.Anuncio;
import br.com.ifpe.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // CRUD de Usuários

    @PostMapping("/usuarios")
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return adminService.criarUsuario(usuario);
    }

    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios() {
        return adminService.listarUsuarios();
    }

    @PutMapping("/usuarios/{id}")
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        return adminService.atualizarUsuario(id, usuarioAtualizado);
    }

    @DeleteMapping("/usuarios/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        adminService.deletarUsuario(id);
    }

    // CRUD de Anúncios

    @GetMapping("/anuncios")
    public List<Anuncio> listarAnuncios() {
        return adminService.listarAnuncios();
    }

    @PutMapping("/anuncios/{id}")
    public Anuncio atualizarAnuncio(@PathVariable Long id, @RequestBody Anuncio anuncioAtualizado) {
        return adminService.atualizarAnuncio(id, anuncioAtualizado);
    }

    @DeleteMapping("/anuncios/{id}")
    public void deletarAnuncio(@PathVariable Long id) {
        adminService.deletarAnuncio(id);
    }
}
