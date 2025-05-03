package br.com.ifpe.demo.service;

import br.com.ifpe.demo.model.Anuncio;
import br.com.ifpe.demo.model.Usuario;
import br.com.ifpe.demo.repository.AnuncioRepository;
import br.com.ifpe.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AnuncioRepository anuncioRepository;

    // Listar todos os usuários
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Excluir usuário
    public boolean excluirUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
            return true;
        }
        return false;
    }

    // Atualizar um usuário
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        if (usuarioRepository.existsById(id)) {
            usuarioAtualizado.setId(id);
            return usuarioRepository.save(usuarioAtualizado);
        }
        return null;
    }

    // Listar todos os anúncios
    public List<Anuncio> listarAnuncios() {
        return anuncioRepository.findAll();
    }

    // Excluir anúncio
    public boolean excluirAnuncio(Long id) {
        Optional<Anuncio> anuncio = anuncioRepository.findById(id);
        if (anuncio.isPresent()) {
            anuncioRepository.delete(anuncio.get());
            return true;
        }
        return false;
    }

    // Atualizar anúncio
    public Anuncio atualizarAnuncio(Long id, Anuncio anuncioAtualizado) {
        if (anuncioRepository.existsById(id)) {
            anuncioAtualizado.setId(id);
            return anuncioRepository.save(anuncioAtualizado);
        }
        return null;
    }
}
