package br.com.ifpe.demo.controller;

import br.com.ifpe.demo.model.Anuncio;
import br.com.ifpe.demo.model.Usuario;
import br.com.ifpe.demo.repository.AnuncioRepository;
import br.com.ifpe.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anuncios")
public class AnuncioController {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Anuncio> listarAnuncios() {
        return anuncioRepository.findAll();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Anuncio> listarAnunciosPorUsuario(@PathVariable Long usuarioId) {
        return anuncioRepository.findByUsuarioId(usuarioId);
    }

    @PostMapping("/usuario/{usuarioId}")
    public Anuncio criarAnuncio(@PathVariable Long usuarioId, @RequestBody Anuncio anuncio) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        anuncio.setUsuario(usuario);
        return anuncioRepository.save(anuncio);
    }

    @PutMapping("/{id}")
    public Anuncio atualizarAnuncio(@PathVariable Long id, @RequestBody Anuncio anuncioAtualizado) {
        anuncioAtualizado.setId(id);
        return anuncioRepository.save(anuncioAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletarAnuncio(@PathVariable Long id) {
        anuncioRepository.deleteById(id);
    }
}
