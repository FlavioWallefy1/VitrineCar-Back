package br.com.ifpe.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.ifpe.demo.service.AnuncioService;

@RestController
@RequestMapping("/anuncios")
public class AnuncioController {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private AnuncioService anuncioService;

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

    @PutMapping("/usuario/{usuarioId}/{anuncioId}")
    public Anuncio editarAnuncio(@PathVariable Long usuarioId, @PathVariable Long anuncioId, @RequestBody Anuncio anuncio) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        Anuncio anuncioExistente = anuncioRepository.findById(anuncioId).orElseThrow();
        
        // Atualiza os dados do anúncio existente
        anuncioExistente.setMarca(anuncio.getMarca());
        anuncioExistente.setModelo(anuncio.getModelo());
        anuncioExistente.setPreco(anuncio.getPreco());
        //anuncioExistente.setAnoFabricacao(anuncio.getAnoFabricacao());
        //anuncioExistente.setAnoModelo(anuncio.getAnoModelo());
        anuncioExistente.setDescricao(anuncio.getDescricao());
        //anuncioExistente.setImagens(anuncio.getImagens());
        
        // Salva o anúncio atualizado
        return anuncioRepository.save(anuncioExistente);
    }


    // @GetMapping
    // public List<Anuncio> listarAnuncio(@RequestParam(required = false) String termo) {
    //     if (termo != null && !termo.isEmpty()) {
    //         return anuncioService.buscarPorTermo(termo);  // Busca por título ou descrição
    //     }
    //     return anuncioService.listarAnuncios();  // Retorna todos os anúncios
    // }


}
