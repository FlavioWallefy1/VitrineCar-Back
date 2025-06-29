package br.com.ifpe.demo.repository;

import br.com.ifpe.demo.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    Optional<Favorito> findByUsuarioIdAndAnuncioId(Long usuarioId, Long anuncioId);
    List<Favorito> findByUsuarioId(Long usuarioId);
    void deleteByUsuarioIdAndAnuncioId(Long usuarioId, Long anuncioId);
}
