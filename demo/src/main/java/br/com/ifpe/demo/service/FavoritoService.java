package br.com.ifpe.demo.service;

import br.com.ifpe.demo.model.Anuncio;
import br.com.ifpe.demo.model.Favorito;
import br.com.ifpe.demo.model.Usuario;
import br.com.ifpe.demo.repository.AnuncioRepository;
import br.com.ifpe.demo.repository.FavoritoRepository;
import br.com.ifpe.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AnuncioRepository anuncioRepository;

    public void favoritar(Long usuarioId, Long anuncioId) {
    boolean jaExiste = favoritoRepository.findByUsuarioIdAndAnuncioId(usuarioId, anuncioId).isPresent();
    if (!jaExiste) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Anuncio anuncio = anuncioRepository.findById(anuncioId)
                .orElseThrow(() -> new RuntimeException("Anúncio não encontrado"));

        Favorito favorito = new Favorito(); // 👈 aqui
        favorito.setUsuario(usuario);
        favorito.setAnuncio(anuncio);

        favoritoRepository.save(favorito);
    }
}

    public void desfavoritar(Long usuarioId, Long anuncioId) {
        Optional<Favorito> favorito = favoritoRepository.findByUsuarioIdAndAnuncioId(usuarioId, anuncioId);
favorito.ifPresent(f -> favoritoRepository.delete(f));

    }

    public List<Favorito> listarFavoritosPorUsuario(Long usuarioId) {
        return favoritoRepository.findByUsuarioId(usuarioId);
    }
}
