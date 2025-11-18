import dao.*;
import model.*;
import view.Menu;

import java.util.Scanner;

public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    UsuarioDAO usuarioDao = new UsuarioDAO();
    AnimalDAO animalDao = new AnimalDAO();
    IngressoDAO ingressoDao = new IngressoDAO();
    CompraDAO compraDao = new CompraDAO();

    Usuario usuario = null;
    String email;
    String senha;
    String escolha;

    Menu inicializador = new Menu(sc, usuarioDao, animalDao, ingressoDao, compraDao);

    do {
        System.out.println("\n===============LOGIN===============");
        System.out.println("[1] - Entrar" +
                "\n[2] - Cadastrar novo usuário" +
                "\n[0] - Sair" +
                "\nEscolha uma das opções acima: ");

        escolha = sc.nextLine().toLowerCase().trim();

        switch (escolha) {
            case "1", "entrar", "e":
                System.out.println("Informe seu email: ");
                email = sc.nextLine();
                System.out.println("Informe sua senha: ");
                senha = sc.nextLine();

                if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
                    System.out.println("Operação inválida! Email e senha não podem ser vazios.");
                    break;
                } else {
                    usuario = usuarioDao.validarUsuario(email, senha);

                    if (usuario == null) {
                        System.out.println("Operação inválida! Email ou senha incorretos. ");
                        break;
                    } else {
                        System.out.println("Logando... ");
                        System.out.println("Logado com sucesso. Bem vindo(a), " + usuario.getNome() + "! ");
                        if (usuario.getTipo().equals("Cliente")){
                            usuario = new Cliente(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha());
                            usuario.mostrarMenuPrincipal(inicializador);
                        } else {
                            usuario = new Administrador(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha());
                            usuario.mostrarMenuPrincipal(inicializador);
                        }
                        break;
                    }
                }

            case "2", "cadastrar", "c":
                System.out.println("Informe o nome do usuário: ");
                String nome = sc.nextLine();
                System.out.println("Informe o email do usuário: ");
                email = sc.nextLine();
                System.out.println("Informe a senha do usuário: ");
                senha = sc.nextLine();
                String tipo = "Cliente";

                if (nome == null || nome.isBlank() || email == null || email.isBlank() || senha == null || senha.isBlank()) {
                    System.out.println("Operação inválida! Nenhum dos campos podem ser vazios.");
                    break;
                }
                Usuario novo_usuario = new Usuario();
                novo_usuario.setNome(nome);
                novo_usuario.setEmail(email);
                novo_usuario.setSenha(senha);
                novo_usuario.setTipo(tipo);

                usuarioDao.salvarUsuario(novo_usuario);
                break;

            case "0", "sair", "s":
                System.out.println("Saindo do sistema...");
                break;

            default:
                System.out.println("Opção inválida! Escolha uma opção válida.");
                break;
        }

    } while (!escolha.equalsIgnoreCase("0") && !escolha.equalsIgnoreCase("sair") && !escolha.equalsIgnoreCase("s"));
}