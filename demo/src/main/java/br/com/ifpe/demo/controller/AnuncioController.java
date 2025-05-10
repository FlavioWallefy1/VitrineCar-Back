package br.com.ifpe.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.demo.model.Anuncio;
import br.com.ifpe.demo.model.Usuario;
import br.com.ifpe.demo.repository.AnuncioRepository;
import br.com.ifpe.demo.repository.UsuarioRepository;


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
    public ResponseEntity<Anuncio> criarAnuncio(@PathVariable Long usuarioId, @RequestBody Anuncio anuncio) {
        try {
            // Verifica se o usuário existe
            Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Vincula o usuário ao anúncio
            anuncio.setUsuario(usuario);

            // Salva o anúncio
            Anuncio novoAnuncio = anuncioRepository.save(anuncio);

            return new ResponseEntity<>(novoAnuncio, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public Anuncio atualizarAnuncio(@PathVariable Long id, @RequestBody Anuncio anuncioAtualizado) {
        anuncioAtualizado.setId(id);
        return anuncioRepository.save(anuncioAtualizado);
    }

    @GetMapping("/{id}")
    public Anuncio buscarAnuncioPorId(@PathVariable Long id) {
        return anuncioRepository.findById(id).orElseThrow(() -> new RuntimeException("Anúncio não encontrado"));
    }


    @DeleteMapping("/{id}")
    public void deletarAnuncio(@PathVariable Long id) {
        anuncioRepository.deleteById(id);
    }


}
