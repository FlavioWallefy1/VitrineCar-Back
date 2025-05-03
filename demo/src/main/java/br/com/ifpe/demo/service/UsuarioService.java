package br.com.ifpe.demo.service;

import br.com.ifpe.demo.model.Anuncio;
import br.com.ifpe.demo.model.Usuario;
import br.com.ifpe.demo.repository.AnuncioRepository;
import br.com.ifpe.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private AnuncioRepository anuncioRepository;

    // Criar um novo usuário
    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Buscar usuário por ID
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Buscar usuário por email
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

     // Listar todos os usuários
     public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Atualizar usuário
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        if (usuarioRepository.existsById(id)) {
            usuarioAtualizado.setId(id);
            return usuarioRepository.save(usuarioAtualizado);
        }
        return null;
    }

    // Remover um usuário
    public void removerUsuario(Long id) {
        // Verificar se o usuário existe
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            // Primeiro, excluir os anúncios associados ao usuário
            List<Anuncio> anuncios = anuncioRepository.findByUsuarioId(id);
            if (!anuncios.isEmpty()) {
                System.out.println("Excluindo " + anuncios.size() + " anúncios relacionados a este usuário...");
                anuncioRepository.deleteAll(anuncios);  // Excluindo os anúncios
            }
            // Agora, excluir o usuário
            usuarioRepository.delete(usuario);
            System.out.println("Usuário excluído com sucesso!");
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }
}
