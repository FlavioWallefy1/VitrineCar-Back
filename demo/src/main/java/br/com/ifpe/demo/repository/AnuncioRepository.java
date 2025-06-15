package br.com.ifpe.demo.repository;

import br.com.ifpe.demo.model.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
    List<Anuncio> findByUsuarioId(Long usuarioId);
   

    // Busca anúncios com base no termo (marca, modelo ou categoria)
    List<Anuncio> findByMarcaContainingIgnoreCaseOrModeloContainingIgnoreCaseOrCategoriaContainingIgnoreCase(String marca, String modelo, String categoria);


    List<Anuncio> findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(String titulo, String descricao);
    List<Anuncio> findTop5ByUsuarioIdOrderByDataCriacaoDesc(Long usuarioId);


    long countByUsuarioIdAndDataCriacaoAfter(Long usuarioId, LocalDateTime dataCriacao);
}
