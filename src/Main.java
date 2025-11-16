import dao.*;
import model.*;

import java.util.Scanner;

public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    UsuarioDAO dao = new UsuarioDAO();
    Usuario usuario;
    String email;
    String senha;
    int escolha;

    do {
        System.out.println("===============LOGIN===============");
        System.out.println("[1] - Entrar" +
                "\n[2] - Cadastrar novo usuário" +
                "\n[0] - Sair" +
                "\nEscolha uma das opções acima: ");
        try {
            escolha = sc.nextInt();
            sc.nextLine();

            switch (escolha) {
                case 1:
                    System.out.println("Informe seu email: ");
                    email = sc.nextLine();
                    System.out.println("Informe sua senha: ");
                    senha = sc.nextLine();

                    if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
                        System.out.println("Operação inválida! Email e senha não podem ser vazios.");
                        break;
                    } else {
                        usuario = dao.validarUsuario(email, senha);

                        if (usuario == null){
                            System.out.println("Operação inválida! Email ou senha incorretos. ");
                            break;
                        } else {
                            System.out.println("Logando... ");
                            System.out.println("Logado com sucesso. Bem vindo(a), " + usuario.getNome() + "! ");
                            escolha = 0;
                            break;
                        }
                    }
                case 2:
                    System.out.println("Você cadastrou um novo usuário! ");
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Escolha uma opção válida.");
                    break;
            }
        } catch (java.util.InputMismatchException e){
            System.out.println("Erro: Entrada inválida. Por favor, digite apenas números.");
            // Consome a próxima linha, para evitar entrar em loop
            sc.nextLine();
            // Reseta a variável para um valor válido, para retomar o loop
            escolha = -1;
        } catch (Exception e) {
            // Captura qualquer outra exceção inesperada
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            sc.nextLine();
            escolha = -1;
        }

    } while(escolha != 0);

    /* for(Usuario u : usuarioDao.listarUsuarios() ){
        System.out.println(u.getNome());
        System.out.println(u.getEmail());
        System.out.println(u.getSenha());
        System.out.println(u.getTipo());
    }

    Usuario a;
    List<Usuario> varios = usuarioDao.listarUsuarios("nome", "Maria");
    if(!varios.isEmpty()){
        a = varios.get(0);
        System.out.println(a.getNome() + a.getEmail() + a.getSenha() + a.getTipo());
    } */

    sc.close();
}
