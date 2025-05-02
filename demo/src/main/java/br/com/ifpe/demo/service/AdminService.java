package br.com.ifpe.demo.service;

import br.com.ifpe.demo.model.Usuario;
import br.com.ifpe.demo.model.Anuncio;
import br.com.ifpe.demo.repository.UsuarioRepository;
import br.com.ifpe.demo.repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AnuncioRepository anuncioRepository;

    // CRUD de Usuários

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        if (usuarioRepository.existsById(id)) {
            usuarioAtualizado.setId(id);
            return usuarioRepository.save(usuarioAtualizado);
        }
        return null;
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // CRUD de Anúncios

    public List<Anuncio> listarAnuncios() {
        return anuncioRepository.findAll();
    }

    public Anuncio atualizarAnuncio(Long id, Anuncio anuncioAtualizado) {
        if (anuncioRepository.existsById(id)) {
            anuncioAtualizado.setId(id);
            return anuncioRepository.save(anuncioAtualizado);
        }
        return null;
    }

    public void deletarAnuncio(Long id) {
        anuncioRepository.deleteById(id);
    }
}
