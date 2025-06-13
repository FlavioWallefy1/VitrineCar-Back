package br.com.ifpe.demo.repository;

import br.com.ifpe.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
=======
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);

    // Método para busca por nome contendo (case insensitive)
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
>>>>>>> main
}
