package br.com.ifpe.demo.controller;

import java.util.Collections;
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
import java.time.Duration;
import java.util.List;
import java.util.Objects;

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

    // Endpoint de busca por marca, modelo ou categoria
    @GetMapping("/buscar")
    public List<Anuncio> buscarAnuncios(@RequestParam String termo) {
        // Realiza a busca no banco com o termo fornecido (marca, modelo ou categoria)
        return anuncioRepository.findByMarcaContainingIgnoreCaseOrModeloContainingIgnoreCaseOrCategoriaContainingIgnoreCase(termo, termo, termo);
    }

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

    @GetMapping("/usuario/{usuarioId}/recentes")
    public List<Anuncio> listarCincoAnunciosMaisRecentes(@PathVariable Long usuarioId) {
        return anuncioService.buscarCincoAnunciosMaisRecentes(usuarioId);
    }

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> criarAnuncio(@PathVariable Long usuarioId, @RequestBody Anuncio anuncio) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            List<Anuncio> ultimosCinco = anuncioRepository.findTop5ByUsuarioIdOrderByDataCriacaoDesc(usuarioId);

            if (ultimosCinco.size() >= LIMITE_ANUNCIOS) {
                LocalDateTime maisAntigo = ultimosCinco.stream()
                        .map(Anuncio::getDataCriacao)
                        .filter(Objects::nonNull)  // segurança
                        .min(LocalDateTime::compareTo)
                        .orElse(LocalDateTime.now());

                LocalDateTime limiteParaNovoAnuncio = maisAntigo.plusDays(PERIODO_DIAS);

                if (LocalDateTime.now().isBefore(limiteParaNovoAnuncio)) {
                    long diasRestantes = Duration.between(LocalDateTime.now(), limiteParaNovoAnuncio).toDays() + 1;
                    String msg = "Limite de 5 anúncios em 30 dias atingido. Aguarde " + diasRestantes + " dia(s) para criar novos.";
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonMap("message", msg));
                }
            }

            anuncio.setUsuario(usuario);
            Anuncio novoAnuncio = anuncioRepository.save(anuncio);

            return ResponseEntity.status(HttpStatus.CREATED).body(novoAnuncio);

        } catch (Exception e) {
            e.printStackTrace();  // ou use log.error("Erro ao criar anúncio", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Erro ao criar anúncio: " + e.getMessage()));
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
