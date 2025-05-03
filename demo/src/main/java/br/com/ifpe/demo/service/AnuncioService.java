package br.com.ifpe.demo.service;

import br.com.ifpe.demo.model.Anuncio;
import br.com.ifpe.demo.repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;

    // Criar novo anúncio
    public Anuncio criarAnuncio(Anuncio anuncio) {
        return anuncioRepository.save(anuncio);
    }

    // Listar todos os anúncios
    public List<Anuncio> listarAnuncios() {
        return anuncioRepository.findAll();
    }

    // Listar anúncios de um usuário específico
    public List<Anuncio> listarAnunciosPorUsuario(Long usuarioId) {
        return anuncioRepository.findByUsuarioId(usuarioId);
    }

    // Atualizar um anúncio
    public Anuncio atualizarAnuncio(Long id, Anuncio anuncioAtualizado) {
        if (anuncioRepository.existsById(id)) {
            anuncioAtualizado.setId(id);
            return anuncioRepository.save(anuncioAtualizado);
        }
        return null;
    }

    // Remover um anúncio
    public boolean removerAnuncio(Long id) {
        Anuncio anuncio = anuncioRepository.findById(id).orElse(null);
        if (anuncio != null) {
            anuncioRepository.delete(anuncio);  // Exclui o anúncio
            return true;  // Anúncio excluído com sucesso
        }
        return false;  // Anúncio não encontrado
    }

    // Buscar um anúncio pelo id
    public Optional<Anuncio> buscarAnuncioPorId(Long id) {
        return anuncioRepository.findById(id);
    }
}