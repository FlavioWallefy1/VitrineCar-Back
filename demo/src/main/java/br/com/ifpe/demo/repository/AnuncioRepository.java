package br.com.ifpe.demo.repository;

import br.com.ifpe.demo.model.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
    List<Anuncio> findByUsuarioId(Long usuarioId);

    List<Anuncio> findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(String titulo, String descricao);

    long countByUsuarioIdAndDataCriacaoAfter(Long usuarioId, LocalDateTime dataCriacao);
}
