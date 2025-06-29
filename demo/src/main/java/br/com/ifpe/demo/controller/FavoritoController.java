package br.com.ifpe.demo.controller;

import br.com.ifpe.demo.model.Favorito;
import br.com.ifpe.demo.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @PostMapping("/usuario/{usuarioId}/anuncio/{anuncioId}")
    public ResponseEntity<?> favoritar(@PathVariable Long usuarioId, @PathVariable Long anuncioId) {
        favoritoService.favoritar(usuarioId, anuncioId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/usuario/{usuarioId}/anuncio/{anuncioId}")
    public ResponseEntity<?> desfavoritar(@PathVariable Long usuarioId, @PathVariable Long anuncioId) {
        favoritoService.desfavoritar(usuarioId, anuncioId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Favorito>> listarFavoritos(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(favoritoService.listarFavoritosPorUsuario(usuarioId));
    }
}
