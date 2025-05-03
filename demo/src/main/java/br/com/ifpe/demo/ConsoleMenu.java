package br.com.ifpe.demo;

import br.com.ifpe.demo.model.Anuncio;
import br.com.ifpe.demo.model.Usuario;
import br.com.ifpe.demo.service.AnuncioService;
import br.com.ifpe.demo.service.UsuarioService;
import br.com.ifpe.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleMenu {

    @Autowired
    private AnuncioService anuncioService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AdminService adminService;  // Adicionando o serviço de Admin

    private Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        int option = 0;

        do {
            System.out.println("\n=== Menu do CRUD de Anúncios ===");
            System.out.println("1. Criar Anúncio");
            System.out.println("2. Listar Anúncios");
            System.out.println("3. Atualizar Anúncio");
            System.out.println("4. Deletar Anúncio");
            System.out.println("\n=== Menu do CRUD de Usuario ===");
            System.out.println("5. Criar Usuário");
            System.out.println("6. Listar Usuários");
            System.out.println("7. Atualizar Usuário");
            System.out.println("8. Deletar Usuário");
            System.out.println("\n=== Menu do Admin ===");  // Menu do Admin
            System.out.println("9. Listar Todos os Usuários (Admin)");
            System.out.println("10. Excluir Usuário (Admin)");
            System.out.println("11. Listar Todos os Anúncios (Admin)");
            System.out.println("12. Excluir Anúncio (Admin)");
            System.out.println("============");
            System.out.println("\n13. Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (option) {
                case 1:
                    createAnuncio();
                    break;
                case 2:
                    listAnuncios();
                    break;
                case 3:
                    updateAnuncio();
                    break;
                case 4:
                    deleteAnuncio();
                    break;
                case 5:
                    createUsuario();
                    break;
                case 6:
                    listUsuarios();
                    break;
                case 7:
                    updateUsuario();
                    break;
                case 8:
                    deleteUsuario();
                    break;
                case 9:
                    adminListUsuarios();  // Listar usuários para admin
                    break;
                case 10:
                    adminDeleteUsuario();  // Excluir usuário para admin
                    break;
                case 11:
                    adminListAnuncios();  // Listar anúncios para admin
                    break;
                case 12:
                    adminDeleteAnuncio();  // Excluir anúncio para admin
                    break;
                case 13:
                    System.out.println("Saindo do menu...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        } while (option != 13);
    }

    // Métodos de CRUD de Anúncio
    private void createAnuncio() {
        System.out.println("\nCriando um novo anúncio:");
        System.out.print("Digite o título do anúncio: ");
        String titulo = scanner.nextLine();
        System.out.print("Digite a descrição do anúncio: ");
        String descricao = scanner.nextLine();
        System.out.print("Digite o preço do veículo: ");
        double preco = scanner.nextDouble();
        scanner.nextLine(); // Limpar o buffer
        System.out.print("Digite a marca do veículo: ");
        String marca = scanner.nextLine();
        System.out.print("Digite o modelo do veículo: ");
        String modelo = scanner.nextLine();
        System.out.print("Digite o ano do veículo: ");
        int ano = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        System.out.print("Digite o ID do usuário (1 para exemplo): ");
        Long usuarioId = scanner.nextLong();
        scanner.nextLine(); // Limpar o buffer

        Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId).orElse(null);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        Anuncio anuncio = new Anuncio();
        anuncio.setTitulo(titulo);
        anuncio.setDescricao(descricao);
        anuncio.setPreco(preco);
        anuncio.setMarca(marca);
        anuncio.setModelo(modelo);
        anuncio.setAno(ano);
        anuncio.setUsuario(usuario);

        anuncioService.criarAnuncio(anuncio);
        System.out.println("Anúncio criado com sucesso!");
    }

    private void listAnuncios() {
        System.out.println("\nListando anúncios:");
        List<Anuncio> anuncios = anuncioService.listarAnuncios();
        if (anuncios.isEmpty()) {
            System.out.println("Não há anúncios disponíveis.");
        } else {
            for (Anuncio anuncio : anuncios) {
                System.out.println("\nID: " + anuncio.getId());
                System.out.println("Título: " + anuncio.getTitulo());
                System.out.println("Descrição: " + anuncio.getDescricao());
                System.out.println("Preço: " + anuncio.getPreco());
                System.out.println("Marca: " + anuncio.getMarca());
                System.out.println("Modelo: " + anuncio.getModelo());
                System.out.println("Ano: " + anuncio.getAno());
                System.out.println("Criado por: " + anuncio.getUsuario().getNome());
                System.out.println("===============");
            }
        }
    }

    private void updateAnuncio() {
        System.out.print("\nDigite o ID do anúncio que você deseja atualizar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpar o buffer

        Anuncio anuncio = anuncioService.buscarAnuncioPorId(id).orElse(null);
        if (anuncio == null) {
            System.out.println("Anúncio não encontrado.");
            return;
        }

        System.out.println("Atualizando o anúncio de ID: " + id);
        System.out.print("Novo título (deixe em branco para manter o atual): ");
        String titulo = scanner.nextLine();
        if (!titulo.isEmpty()) {
            anuncio.setTitulo(titulo);
        }

        System.out.print("Nova descrição (deixe em branco para manter a atual): ");
        String descricao = scanner.nextLine();
        if (!descricao.isEmpty()) {
            anuncio.setDescricao(descricao);
        }

        System.out.print("Novo preço (0 para manter o atual): ");
        double preco = scanner.nextDouble();
        scanner.nextLine(); // Limpar o buffer
        if (preco > 0) {
            anuncio.setPreco(preco);
        }

        System.out.print("Nova marca (deixe em branco para manter a atual): ");
        String marca = scanner.nextLine();
        if (!marca.isEmpty()) {
            anuncio.setMarca(marca);
        }

        System.out.print("Novo modelo (deixe em branco para manter o atual): ");
        String modelo = scanner.nextLine();
        if (!modelo.isEmpty()) {
            anuncio.setModelo(modelo);
        }

        System.out.print("Novo ano (0 para manter o atual): ");
        int ano = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer
        if (ano > 0) {
            anuncio.setAno(ano);
        }

        anuncioService.atualizarAnuncio(id, anuncio);
        System.out.println("Anúncio atualizado com sucesso!");
    }

    private void deleteAnuncio() {
        System.out.print("\nDigite o ID do anúncio que você deseja excluir: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpar o buffer
    
        try {
            // Tentando remover o anúncio
            boolean sucesso = anuncioService.removerAnuncio(id);
    
            if (sucesso) {
                System.out.println("Anúncio excluído com sucesso!");
            } else {
                System.out.println("Anúncio não encontrado ou já excluído.");
            }
        } catch (Exception e) {
            // Se houver erro na exclusão, exibe a mensagem
            System.out.println("Erro ao excluir o anúncio: " + e.getMessage());
        }
    }

    // Métodos de CRUD de Usuário
    private void createUsuario() {
        System.out.println("\nCriando um novo usuário:");
        System.out.print("Digite o nome do usuário: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o email do usuário: ");
        String email = scanner.nextLine();
        System.out.print("Digite a senha do usuário: ");
        String senha = scanner.nextLine();

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        usuarioService.criarUsuario(usuario);
        System.out.println("Usuário criado com sucesso!");
    }

    private void listUsuarios() {
        System.out.println("\nListando usuários:");
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("Não há usuários cadastrados.");
        } else {
            for (Usuario usuario : usuarios) {
                System.out.println("\nID: " + usuario.getId());
                System.out.println("Nome: " + usuario.getNome());
                System.out.println("Email: " + usuario.getEmail());
                System.out.println("===============");
            }
        }
    }

    private void updateUsuario() {
        System.out.print("\nDigite o ID do usuário que você deseja atualizar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpar o buffer

        Usuario usuario = usuarioService.buscarUsuarioPorId(id).orElse(null);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.println("Atualizando o usuário de ID: " + id);
        System.out.print("Novo nome (deixe em branco para manter o atual): ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) {
            usuario.setNome(nome);
        }

        System.out.print("Novo email (deixe em branco para manter o atual): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            usuario.setEmail(email);
        }

        System.out.print("Nova senha (deixe em branco para manter a atual): ");
        String senha = scanner.nextLine();
        if (!senha.isEmpty()) {
            usuario.setSenha(senha);
        }

        usuarioService.atualizarUsuario(id, usuario);
        System.out.println("Usuário atualizado com sucesso!");
    }

    private void deleteUsuario() {
        System.out.print("\nDigite o ID do usuário que você deseja excluir: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpar o buffer

        usuarioService.removerUsuario(id);
        System.out.println("Usuário excluído com sucesso!");
    }

    // Métodos de CRUD de Admin
    private void adminListUsuarios() {
        List<Usuario> usuarios = adminService.listarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("Não há usuários.");
        } else {
            for (Usuario usuario : usuarios) {
                System.out.println("ID: " + usuario.getId() + " | Nome: " + usuario.getNome() + " | Email: " + usuario.getEmail());
            }
        }
    }

    private void adminDeleteUsuario() {
        System.out.print("Digite o ID do usuário para excluir: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        if (adminService.excluirUsuario(id)) {
            System.out.println("Usuário excluído com sucesso!");
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    private void adminListAnuncios() {
        List<Anuncio> anuncios = adminService.listarAnuncios();
        if (anuncios.isEmpty()) {
            System.out.println("Não há anúncios.");
        } else {
            for (Anuncio anuncio : anuncios) {
                System.out.println("ID: " + anuncio.getId() + " | Título: " + anuncio.getTitulo() + " | Preço: " + anuncio.getPreco());
            }
        }
    }

    private void adminDeleteAnuncio() {
        System.out.print("Digite o ID do anúncio para excluir: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        if (adminService.excluirAnuncio(id)) {
            System.out.println("Anúncio excluído com sucesso!");
        } else {
            System.out.println("Anúncio não encontrado.");
        }
    }
}
