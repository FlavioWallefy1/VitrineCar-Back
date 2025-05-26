package br.com.ifpe.demo.service;

import br.com.ifpe.demo.model.Anuncio;
import br.com.ifpe.demo.repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;

    public Anuncio criarAnuncio(Anuncio anuncio) {
        return anuncioRepository.save(anuncio);
    }

    public List<Anuncio> listarAnuncios() {
        return anuncioRepository.findAll();
    }

    public List<Anuncio> listarAnunciosPorUsuario(Long usuarioId) {
        return anuncioRepository.findByUsuarioId(usuarioId);
    }

    public Anuncio atualizarAnuncio(Long id, Anuncio anuncioAtualizado) {
        if (anuncioRepository.existsById(id)) {
            anuncioAtualizado.setId(id);
            return anuncioRepository.save(anuncioAtualizado);
        }
        return null;
    }

    public boolean removerAnuncio(Long id) {
        Anuncio anuncio = anuncioRepository.findById(id).orElse(null);
        if (anuncio != null) {
            anuncioRepository.delete(anuncio);
            return true;
        }
        return false;
    }

    public Optional<Anuncio> buscarAnuncioPorId(Long id) {
        return anuncioRepository.findById(id);
    }

    public List<Anuncio> buscarPorTermo(String termo) {
        return anuncioRepository.findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(termo, termo);
    }

    public long contarAnunciosRecentes(Long usuarioId, LocalDateTime dataLimite) {
        return anuncioRepository.countByUsuarioIdAndDataCriacaoAfter(usuarioId, dataLimite);
    }

    public List<Anuncio> buscarCincoAnunciosMaisRecentes(Long usuarioId) {
    return anuncioRepository.findTop5ByUsuarioIdOrderByDataCriacaoDesc(usuarioId);
}

}
