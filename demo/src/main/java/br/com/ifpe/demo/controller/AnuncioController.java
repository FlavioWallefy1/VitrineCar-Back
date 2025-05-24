package br.com.ifpe.demo.controller;

import br.com.ifpe.demo.model.Anuncio;
import br.com.ifpe.demo.model.Usuario;
import br.com.ifpe.demo.repository.AnuncioRepository;
import br.com.ifpe.demo.repository.UsuarioRepository;
import br.com.ifpe.demo.service.AnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/anuncios")
public class AnuncioController {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AnuncioService anuncioService;

    private static final int LIMITE_ANUNCIOS = 5;
    private static final int PERIODO_DIAS = 30;

    @GetMapping
    public List<Anuncio> listarAnuncios() {
        return anuncioRepository.findAll();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Anuncio> listarAnunciosPorUsuario(@PathVariable Long usuarioId) {
        return anuncioRepository.findByUsuarioId(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/count")
    public long contarAnunciosRecentes(@PathVariable Long usuarioId) {
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(PERIODO_DIAS);
        return anuncioService.contarAnunciosRecentes(usuarioId, dataLimite);
    }

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> criarAnuncio(@PathVariable Long usuarioId, @RequestBody Anuncio anuncio) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            LocalDateTime dataLimite = LocalDateTime.now().minusDays(PERIODO_DIAS);
            long quantidadeAnuncios = anuncioService.contarAnunciosRecentes(usuarioId, dataLimite);

            if (quantidadeAnuncios >= LIMITE_ANUNCIOS) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Limite de 5 anúncios em 30 dias atingido. Aguarde para criar novos.");
            }

            anuncio.setUsuario(usuario);
            Anuncio novoAnuncio = anuncioRepository.save(anuncio);
            return new ResponseEntity<>(novoAnuncio, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Limite de 5 anúncios")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
