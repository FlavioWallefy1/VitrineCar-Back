package br.com.ifpe.demo;

import br.com.ifpe.demo.model.*;
import br.com.ifpe.demo.service.*;
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

    private Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        int option = 0;

        do {
            System.out.println("\n=== Menu do CRUD de Veículos ===");
            System.out.println("1. Criar Anúncio");
            System.out.println("2. Listar Anúncios");
            System.out.println("3. Atualizar Anúncio");
            System.out.println("4. Deletar Anúncio");
            System.out.println("5. Criar Usuário");
            System.out.println("6. Sair");
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
                    System.out.println("Saindo do menu...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        } while (option != 6);
    }

    private void createAnuncio() {
        System.out.println("\nCriando um novo anúncio:");

        // Solicitar dados do veículo
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

        // Solicitar usuário (usando um ID fixo ou simulando a criação de um usuário)
        System.out.print("Digite o ID do usuário (1 para exemplo): ");
        Long usuarioId = scanner.nextLong();
        scanner.nextLine(); // Limpar o buffer

        // Recuperar usuário
        Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId).orElse(null);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        // Criar anúncio
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
            }
        }
    }

    private void updateAnuncio() {
        System.out.print("\nDigite o ID do anúncio que você deseja atualizar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpar o buffer

        // Procurar o anúncio
        Anuncio anuncio = anuncioService.buscarAnuncioPorId(id).orElse(null);

        if (anuncio == null) {
            System.out.println("Anúncio não encontrado.");
            return;
        }

        System.out.println("Atualizando o anúncio de ID: " + id);

        // Atualizar os campos
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

        // Remover o anúncio
        anuncioService.removerAnuncio(id);
        System.out.println("Anúncio excluído com sucesso!");
    }

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
}