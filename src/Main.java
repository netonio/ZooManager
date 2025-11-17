import dao.*;
import model.*;

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
    int contador = 1;

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

                    if (usuario == null){
                        System.out.println("Operação inválida! Email ou senha incorretos. ");
                        break;
                    } else {
                        System.out.println("Logando... ");
                        System.out.println("Logado com sucesso. Bem vindo(a), " + usuario.getNome() + "! ");
                        escolha = "0";
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

                if (nome == null || nome.isBlank() || email == null || email.isBlank() || senha == null || senha.isBlank()){
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

    } while(!escolha.equalsIgnoreCase("0") && !escolha.equalsIgnoreCase("sair") && !escolha.equalsIgnoreCase("s"));

    if (usuario.getTipo().equals("Cliente")) {
        do {
            System.out.println("\n===============MENU CLIENTE===============");
            System.out.println("[1] - Ver animais" +
                    "\n[2] - Ver ingressos" +
                    "\n[3] - Comprar ingressos" +
                    "\n[4] - Ver compras" +
                    "\n[5] - Voltar ao menu anterior" +
                    "\n[0] - Sair" +
                    "\nEscolha uma das opções acima: ");

            escolha = sc.nextLine().toLowerCase().trim();

            switch (escolha) {
                case "1", "veranimais", "animais":
                    System.out.println("Gostaria de aplicar algum filtro? (s/n)");
                    String filtrar_animal = sc.nextLine().toLowerCase().trim();

                    if (filtrar_animal.equals("s")) {
                        System.out.println("[1] - Nome" +
                                "\n[2] - Categoria" +
                                "\n[3] - Espécie" +
                                "\n[4] - Habitat" +
                                "\n[5] - Alimentação" +
                                "\n[6] - Descrição" +
                                "\n[7] - Voltar ao menu anterior" +
                                "\nDigite o nome da coluna que gostaria de aplicar o filtro: ");
                        String coluna_filtro = sc.nextLine().toLowerCase().trim();
                        String animal_filtro;

                        switch (coluna_filtro) {
                            case "nome", "categoria", "espécie", "habitat", "alimentação", "descrição":
                                System.out.println("Qual filtro?");
                                animal_filtro = sc.nextLine().toLowerCase().trim();

                                for(Animal a : animalDao.listarAnimais(coluna_filtro, animal_filtro)){
                                    if(a == null){
                                        System.out.println("Nenhum animal encontrado! ");
                                        break;
                                    }

                                    System.out.println("\n----------Animal " + contador + "----------");
                                    System.out.println("Nome: " + a.getNome());
                                    System.out.println("Categoria: " + a.getCategoria());
                                    System.out.println("Espécie: " + a.getEspecie());
                                    System.out.println("Idade: " + a.getIdade());
                                    System.out.println("Habitat: " + a.getHabitat());
                                    System.out.println("Alimentação: " + a.getAlimentacao());
                                    System.out.println("Descrição: " + a.getDescricao());

                                    contador++;
                                }
                                break;

                            case "voltar", "v":
                                System.out.println("Voltando...");
                                break;

                            default:
                                System.out.println("Opção inválida! Escolha uma opção válida.");
                                break;
                        }
                    } else {
                        for(Animal a : animalDao.listarAnimais()){
                            if(a == null){
                                System.out.println("Nenhum animal encontrado! ");
                                break;
                            }

                            System.out.println("\n----------Animal " + contador + "----------");
                            System.out.println("Nome: " + a.getNome());
                            System.out.println("Categoria: " + a.getCategoria());
                            System.out.println("Espécie: " + a.getEspecie());
                            System.out.println("Idade: " + a.getIdade());
                            System.out.println("Habitat: " + a.getHabitat());
                            System.out.println("Alimentação: " + a.getAlimentacao());
                            System.out.println("Descrição: " + a.getDescricao());

                            contador++;
                        }
                    }
                    break;

                case "2", "veringressos", "ingressos":
                    System.out.println("Gostaria de aplicar algum filtro? (s/n)");
                    String filtrar_ingresso = sc.nextLine().toLowerCase().trim();

                    if (filtrar_ingresso.equals("s")) {
                        System.out.println("[1] - Nome" +
                                "\n[2] - Descrição" +
                                "\n[3] - Preço" +
                                "\n[4] - Voltar ao menu anterior" +
                                "\nDigite o nome da coluna que gostaria de aplicar o filtro:  ");
                        String coluna_filtro = sc.nextLine().toLowerCase().trim();
                        String ingresso_filtro;

                        switch (coluna_filtro) {
                            case "nome", "descrição":
                                System.out.println("Qual filtro?");
                                ingresso_filtro = sc.nextLine().toLowerCase().trim();

                            for(Ingresso i : ingressoDao.listarIngressos(coluna_filtro, ingresso_filtro, null)){
                                if(i == null){
                                    System.out.println("Nenhum animal encontrado! ");
                                    break;
                                }
                                System.out.println("\n----------Ingresso " + contador + "----------");
                                System.out.println("ID: " + i.getId());
                                System.out.println("Nome: " + i.getNome());
                                System.out.println("Descrição: " + i.getDescricao());
                                System.out.println("Preço: " + i.getPreco());
                                System.out.println("Quantidade: " + i.getQuantidade());

                                contador++;
                            }
                                break;

                            case "preco":
                                System.out.println("Qual preço?");
                                double filtro_preco = sc.nextDouble();
                                sc.nextLine();

                                for(Ingresso i : ingressoDao.listarIngressos(coluna_filtro, null, filtro_preco)){
                                    if(i == null){
                                        System.out.println("Nenhum animal encontrado! ");
                                        break;
                                    }

                                    System.out.println("\n----------Ingresso " + contador + "----------");
                                    System.out.println("ID: " + i.getId());
                                    System.out.println("Nome: " + i.getNome());
                                    System.out.println("Descrição: " + i.getDescricao());
                                    System.out.println("Preço: " + i.getPreco());
                                    System.out.println("Quantidade: " + i.getQuantidade());

                                    contador++;
                                }
                                break;

                            case "voltar", "v":
                                System.out.println("Voltando...");
                                break;

                            default:
                                System.out.println("Opção inválida! Escolha uma opção válida.");
                                break;
                        }

                    } else {
                        for(Ingresso i : ingressoDao.listarIngressos()){
                            if(i == null){
                                System.out.println("Nenhum animal encontrado! ");
                                break;
                            }

                            System.out.println("\n----------Ingresso " + contador + "----------");
                            System.out.println("ID: " + i.getId());
                            System.out.println("Nome: " + i.getNome());
                            System.out.println("Descrição: " + i.getDescricao());
                            System.out.println("Preço: " + i.getPreco());
                            System.out.println("Quantidade: " + i.getQuantidade());

                            contador++;
                        }
                    }
                    break;

                case "3", "comprar", "compraringressos":
                    System.out.println("Comprando...");
                    break;

                case "4", "compras", "vercompras":
                    Compra c = compraDao.buscarPorId(usuario.getId());
                    if(c == null){
                        System.out.println("Operação inválida! Nenhuma compra encontrada. ");
                        break;
                    }

                    Usuario nome_usuario = usuarioDao.buscarPorId(c.getId_usuario());
                    Ingresso nome_ingresso = ingressoDao.buscarPorId(c.getId_ingresso());

                    System.out.println("\n----------Ingresso " + contador + "----------");
                    System.out.println("ID: " + c.getId());
                    System.out.println("ID Cliente: " + c.getId_usuario());
                    System.out.println("Nome cliente: " + nome_usuario.getNome());
                    System.out.println("ID Ingresso: " + c.getId_ingresso());
                    System.out.println("Nome ingresso: " + nome_ingresso.getNome());
                    System.out.println("Quantidade: " + c.getQuantidade());
                    System.out.println("Valor: " + c.getValor());

                    contador++;
                    break;

                case "5", "voltar", "v":
                    System.out.println("Voltando...");
                    break;

                case "0", "sair", "s":
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção inválida! Escolha uma opção válida.");
                    break;
            }
        } while (!escolha.equalsIgnoreCase("0") && !escolha.equalsIgnoreCase("sair") && !escolha.equalsIgnoreCase("s"));
    } else {
        do {
            System.out.println("\n===============MENU ADM===============");
            System.out.println("[1] - Animais" +
                    "\n[2] - Usuários" +
                    "\n[3] - Ingressos" +
                    "\n[4] - Compras" +
                    "\n[5] - Voltar ao menu anterior" +
                    "\n[0] - Sair" +
                    "\nEscolha uma das opções acima: ");

            escolha = sc.nextLine().toLowerCase().trim();

            switch(escolha){
                case "1", "animais", "a":
                    do {
                        System.out.println("\n===============MENU ANIMAIS===============");
                        System.out.println("[1] - Adicionar animais" +
                                "\n[2] - Ver animais" +
                                "\n[3] - Editar animais" +
                                "\n[4] - Deletar animais" +
                                "\n[5] - Voltar ao menu anterior" +
                                "\n[0] - Sair" +
                                "\nEscolha uma das opções acima: ");

                        escolha = sc.nextLine().toLowerCase().trim();

                        switch (escolha){
                            case "1", "adicionaranimais", "adicionar":
                                System.out.println("Informe o nome do animal: ");
                                String nome = sc.nextLine();
                                System.out.println("Informe a categoria do animal: ");
                                String categoria = sc.nextLine();
                                System.out.println("Informe a espécie do animal: ");
                                String especie = sc.nextLine();
                                System.out.println("Informe a idade do animal: ");
                                int idade = sc.nextInt();
                                sc.nextLine();
                                System.out.println("Informe o habitat do animal: ");
                                String habitat = sc.nextLine();
                                System.out.println("Informe a alimentaçao do animal: ");
                                String alimentacao = sc.nextLine();
                                System.out.println("Informe a descrição do animal: ");
                                String descricao = sc.nextLine();

                                Animal novo_animal = new Animal();

                                novo_animal.setNome(nome);
                                novo_animal.setCategoria(categoria);
                                novo_animal.setEspecie(especie);
                                novo_animal.setIdade(idade);
                                novo_animal.setHabitat(habitat);
                                novo_animal.setAlimentacao(alimentacao);
                                novo_animal.setDescricao(descricao);

                                animalDao.salvarAnimal(novo_animal);

                                break;

                            case "2", "veranimais", "ver":
                                System.out.println("Gostaria de aplicar algum filtro? (s/n)");
                                String filtrar_animal = sc.nextLine().toLowerCase().trim();

                                if (filtrar_animal.equals("s")) {
                                    System.out.println("[1] - ID" +
                                            "\n[2] - Nome" +
                                            "\n[3] - Categoria" +
                                            "\n[4] - Espécie" +
                                            "\n[5] - Habitat" +
                                            "\n[6] - Alimentação" +
                                            "\n[7] - Descrição" +
                                            "\n[8] - Voltar ao menu anterior" +
                                            "\nDigite o nome da coluna que deseja aplicar o filtro: ");
                                    String coluna_filtro = sc.nextLine().toLowerCase().trim();
                                    String animal_filtro;

                                    switch (coluna_filtro) {
                                        case "id":
                                            System.out.println("Qual ID?");
                                            int id_animal = sc.nextInt();
                                            sc.nextLine();

                                            Animal a = animalDao.buscarPorId(id_animal);
                                            if(a == null){
                                                System.out.println("Nenhum animal encontrado! ");
                                                break;
                                            }
                                            System.out.println("\n----------Animal " + contador + "----------");
                                            System.out.println("ID: " + a.getId());
                                            System.out.println("Nome: " + a.getNome());
                                            System.out.println("Categoria: " + a.getCategoria());
                                            System.out.println("Espécie: " + a.getEspecie());
                                            System.out.println("Idade: " + a.getIdade());
                                            System.out.println("Habitat: " + a.getHabitat());
                                            System.out.println("Alimentação: " + a.getAlimentacao());
                                            System.out.println("Descrição: " + a.getDescricao());

                                            contador++;
                                            break;

                                        case "nome", "categoria", "espécie", "habitat", "alimentação", "descrição":
                                            System.out.println("Qual filtro?");
                                            animal_filtro = sc.nextLine().toLowerCase().trim();

                                            for(Animal b : animalDao.listarAnimais(coluna_filtro, animal_filtro)){
                                                if(b == null){
                                                    System.out.println("Nenhum animal encontrado! ");
                                                    break;
                                                }

                                                System.out.println("\n----------Animal " + contador + "----------");
                                                System.out.println("ID: " + b.getId());
                                                System.out.println("Nome: " + b.getNome());
                                                System.out.println("Categoria: " + b.getCategoria());
                                                System.out.println("Espécie: " + b.getEspecie());
                                                System.out.println("Idade: " + b.getIdade());
                                                System.out.println("Habitat: " + b.getHabitat());
                                                System.out.println("Alimentação: " + b.getAlimentacao());
                                                System.out.println("Descrição: " + b.getDescricao());

                                                contador++;
                                            }
                                            break;

                                        case "voltar", "v":
                                            System.out.println("Voltando...");
                                            break;

                                        default:

                                            System.out.println("Opção inválida! Escolha uma opção válida.");
                                            break;
                                    }
                                } else if (filtrar_animal.equals("n")){
                                    for(Animal a : animalDao.listarAnimais()){
                                        if(a == null){
                                            System.out.println("Nenhum animal encontrado! ");
                                            break;
                                        }

                                        System.out.println("\n----------Animal " + contador + "----------");
                                        System.out.println("ID: " + a.getId());
                                        System.out.println("Nome: " + a.getNome());
                                        System.out.println("Categoria: " + a.getCategoria());
                                        System.out.println("Espécie: " + a.getEspecie());
                                        System.out.println("Idade: " + a.getIdade());
                                        System.out.println("Habitat: " + a.getHabitat());
                                        System.out.println("Alimentação: " + a.getAlimentacao());
                                        System.out.println("Descrição: " + a.getDescricao());
                                    }
                                } else {
                                    System.out.println("Opção inválida! Digite uma opção válida.");
                                }
                                break;

                            case "3", "atualizaranimais", "atualizar":
                                System.out.println("Qual ID do animal que deseja atualizar? ");
                                int id_animal_atualizar = sc.nextInt();
                                sc.nextLine();

                                Animal animal_atualizar_existe = animalDao.buscarPorId((id_animal_atualizar));
                                if(animal_atualizar_existe == null){
                                    System.out.println("Operação inválida! Nenhum animal encontrado. ");
                                    break;
                                }
                                System.out.println("Gostaria de atualizar uma ou mais informações? (uma/varias)");
                                String quant_informacoes = sc.nextLine().toLowerCase().trim();

                                if (quant_informacoes.equals("uma")) {
                                    System.out.println("[1] - Nome" +
                                            "\n[2] - Categoria" +
                                            "\n[3] - Espécie" +
                                            "\n[4] - Idade" +
                                            "\n[5] - Habitat" +
                                            "\n[6] - Alimentação" +
                                            "\n[7] - Descrição" +
                                            "\n[8] - Voltar ao menu anterior" +
                                            "\nDigite o nome da coluna que deseja fazer a atualização: ");

                                    String coluna = sc.nextLine().toLowerCase().trim();
                                    String informacao;

                                    switch (coluna) {
                                        case "nome", "categoria", "espécie", "habitat", "alimentação", "descrição":
                                            System.out.println("Qual informação?");
                                            informacao = sc.nextLine();
                                            animalDao.atualizarAnimal(id_animal_atualizar, coluna, informacao, null);

                                            break;
                                        case "idade":
                                            System.out.println("Qual idade? ");
                                            int nova_idade = sc.nextInt();
                                            sc.nextLine();
                                            animalDao.atualizarAnimal(id_animal_atualizar, coluna, null, nova_idade);
                                            break;

                                        case "voltar", "v":
                                            System.out.println("Voltando...");
                                            break;

                                        default:
                                            System.out.println("Opção inválida! Escolha uma opção válida.");
                                            break;
                                    }
                                } else if (quant_informacoes.equals("varias")) {
                                    System.out.println("Informe o novo nome: ");
                                    String novo_nome = sc.nextLine();
                                    System.out.println("Informe a nova categoria: ");
                                    String nova_categoria = sc.nextLine();
                                    System.out.println("Informe a nova espécie: ");
                                    String nova_especie = sc.nextLine();
                                    System.out.println("Informe a nova idade: ");
                                    int nova_idade = sc.nextInt();
                                    sc.nextLine();
                                    System.out.println("Informe o novo habitat: ");
                                    String novo_habitat = sc.nextLine();
                                    System.out.println("Informe a nova alimentação: ");
                                    String nova_alimentacao = sc.nextLine();
                                    System.out.println("Informe a nova descrição: ");
                                    String nova_descricao = sc.nextLine();

                                    Animal animal_atualizado = new Animal();
                                    animal_atualizado.setId(id_animal_atualizar);
                                    animal_atualizado.setNome(novo_nome);
                                    animal_atualizado.setCategoria(nova_categoria);
                                    animal_atualizado.setEspecie(nova_especie);
                                    animal_atualizado.setIdade(nova_idade);
                                    animal_atualizado.setHabitat(novo_habitat);
                                    animal_atualizado.setAlimentacao(nova_alimentacao);
                                    animal_atualizado.setDescricao(nova_descricao);

                                    animalDao.atualizarAnimal(animal_atualizado);
                                } else {
                                    System.out.println("Opção inválida! Digite uma opção válida.");
                                }
                                break;

                            case "4", "deletaranimais", "deletar", "d":
                                System.out.println("Qual ID do animal que você quer deletar? ");
                                int id_animal_deletar = sc.nextInt();
                                sc.nextLine();

                                Animal animal_deletar = animalDao.buscarPorId(id_animal_deletar);
                                if(animal_deletar == null){
                                    System.out.println("Operação inválida! Nenhum animal encontrado. ");
                                    break;
                                }

                                System.out.println("Nome: " + animal_deletar.getNome() +
                                                    "\nEspecie: " + animal_deletar.getEspecie() +
                                                    "\nIdade: " + animal_deletar.getIdade() +
                                                    "\nDescrição: " + animal_deletar.getDescricao() +
                                                    "\nTem certeza que deseja deletar este animal? (s/n)");
                                String deletar = sc.nextLine().toLowerCase().trim();

                                if (deletar.equals("s")) {
                                    animalDao.deletarAnimalPorId(id_animal_deletar);
                                } else {
                                    System.out.println("O animal não foi deletado! ");
                                }
                                break;

                            case "5", "voltar", "v":
                                System.out.println("Voltando...");
                                break;

                            case "0", "sair", "s":
                                System.out.println("Saindo do sistema...");
                                break;

                            default:
                                System.out.println("Opção inválida! Escolha uma opção válida.");
                                break;
                        }

                    } while (!escolha.equalsIgnoreCase("0") && !escolha.equalsIgnoreCase("sair") && !escolha.equalsIgnoreCase("s"));

                case "2", "usuarios", "u":
                    do {
                        System.out.println("\n===============MENU USUÁRIOS===============");
                        System.out.println("[1] - Adicionar usuários" +
                                "\n[2] - Ver usuários" +
                                "\n[3] - Editar usuários" +
                                "\n[4] - Deletar usuários" +
                                "\n[5] - Voltar ao menu anterior" +
                                "\n[0] - Sair" +
                                "\nEscolha uma das opções acima: ");

                        escolha = sc.nextLine().toLowerCase().trim();

                        switch (escolha){
                            case "1", "adicionarusuarios", "adicionar":
                                System.out.println("Informe o nome do usuário: ");
                                String nome = sc.nextLine();
                                System.out.println("Informe o email do usuário: ");
                                email = sc.nextLine();
                                System.out.println("Informe a senha do usuário: ");
                                senha = sc.nextLine();
                                System.out.println("Informe a permissão do usuário (ADM/Cliente): ");
                                String tipo = sc.nextLine();

                                Usuario novo_usuario = new Usuario();
                                novo_usuario.setNome(nome);
                                novo_usuario.setEmail(email);
                                novo_usuario.setSenha(senha);
                                novo_usuario.setTipo(tipo);

                                usuarioDao.salvarUsuario(novo_usuario);
                                break;

                            case "2", "verusuarios", "ver":
                                System.out.println("Gostaria de aplicar algum filtro? (s/n)");
                                String filtrar_usuario = sc.nextLine().toLowerCase().trim();

                                if (filtrar_usuario.equals("s")) {
                                    System.out.println("[1] - ID" +
                                            "\n[2] - Nome" +
                                            "\n[3] - Email" +
                                            "\n[4] - Tipo" +
                                            "\n[5] - Voltar ao menu anterior" +
                                            "\nDigite o nome da coluna que deseja aplicar o filtro: ");
                                    String coluna_filtro = sc.nextLine().toLowerCase().trim();
                                    String usuario_filtro;

                                    switch (coluna_filtro) {
                                        case "id":
                                            System.out.println("Informe o ID: ");
                                            int id_usuario = sc.nextInt();
                                            sc.nextLine();

                                            Usuario u = usuarioDao.buscarPorId(id_usuario);
                                            if(u == null){
                                                System.out.println("Nenhum usuário encontrado! ");
                                                break;
                                            }
                                            System.out.println("\n----------Usuário----------");
                                            System.out.println("ID: " + u.getId());
                                            System.out.println("Nome: " + u.getNome());
                                            System.out.println("Email: " + u.getEmail());
                                            System.out.println("Tipo: " + u.getTipo());

                                            break;

                                        case "nome", "tipo":
                                            System.out.println("Qual filtro?");
                                            usuario_filtro = sc.nextLine();

                                            for(Usuario b : usuarioDao.listarUsuarios(coluna_filtro, usuario_filtro)){
                                                if(b == null){
                                                    System.out.println("Nenhum usuário encontrado! ");
                                                    break;
                                                }
                                                System.out.println("\n----------Usuário " + contador + "----------");
                                                System.out.println("ID: " + b.getId());
                                                System.out.println("Nome: " + b.getNome());
                                                System.out.println("Email: " + b.getEmail());
                                                System.out.println("Tipo: " + b.getTipo());

                                                contador++;
                                            }
                                            break;

                                        case "email":
                                            System.out.println("Informe o email: ");
                                            String email_filtro = sc.nextLine();

                                            Usuario a = usuarioDao.buscarPorEmail(email_filtro);
                                            if(a == null){
                                                System.out.println("Nenhum usuário encontrado! ");
                                                break;
                                            }
                                            System.out.println("\n----------Usuário----------");
                                            System.out.println("ID: " + a.getId());
                                            System.out.println("Nome: " + a.getNome());
                                            System.out.println("Email: " + a.getEmail());
                                            System.out.println("Tipo: " + a.getTipo());
                                            break;

                                        case "voltar", "v":
                                            System.out.println("Voltando...");
                                            break;

                                        default:
                                            System.out.println("Opção inválida! Escolha uma opção válida.");
                                            break;
                                    }
                                } else if (filtrar_usuario.equals("n")) {
                                    for(Usuario a : usuarioDao.listarUsuarios()){
                                        if(a == null){
                                            System.out.println("Nenhum usuário encontrado! ");
                                            break;
                                        }
                                        System.out.println("\n----------Usuário " + contador + "----------");
                                        System.out.println("ID: " + a.getId());
                                        System.out.println("Nome: " + a.getNome());
                                        System.out.println("Email: " + a.getEmail());
                                        System.out.println("Tipo: " + a.getTipo());

                                        contador++;
                                    }
                                } else {
                                    System.out.println("Opção inválida! Digite uma opção válida.");
                                }
                                break;

                            case "3", "atualizarusuarios", "atualizar":
                                System.out.println("Qual ID do usuário deseja atualizar? ");
                                int id_usuario_atualizar = sc.nextInt();
                                sc.nextLine();

                                Usuario usuario_atualizar = usuarioDao.buscarPorId(id_usuario_atualizar);
                                if(usuario_atualizar == null){
                                    System.out.println("Nenhum usuário encontrado! ");
                                    break;
                                }

                                System.out.println("Gostaria de atualizar uma ou mais informações? (uma/varias)");
                                String quant_informacoes = sc.nextLine().toLowerCase().trim();

                                if (quant_informacoes.equals("uma") || quant_informacoes.equals("u")) {
                                    System.out.println("[1] - Nome" +
                                            "\n[2] - Email" +
                                            "\n[3] - Senha" +
                                            "\n[4] - Tipo" +
                                            "\n[5] - Voltar ao menu anterior" +
                                            "\nDigite o nome da coluna que deseja fazer a atualização: ");

                                    String coluna = sc.nextLine().toLowerCase().trim();
                                    String informacao;

                                    switch (coluna) {
                                        case "nome", "email", "senha", "tipo":
                                            System.out.println("Qual informação?");
                                            informacao = sc.nextLine();
                                            usuarioDao.atualizarUsuario(id_usuario_atualizar, coluna, informacao);
                                            break;

                                        case "voltar", "v":
                                            System.out.println("Voltando...");
                                            break;

                                        default:
                                            System.out.println("Opção inválida! Escolha uma opção válida.");
                                            break;
                                    }
                                } else if (quant_informacoes.equals("varias") || quant_informacoes.equals("v")) {
                                    System.out.println("Informe o novo nome: ");
                                    String novo_nome = sc.nextLine();
                                    System.out.println("Informe o novo email: ");
                                    String novo_email = sc.nextLine();
                                    System.out.println("Informe a nova senha: ");
                                    String nova_senha = sc.nextLine();
                                    System.out.println("Informe o novo tipo: ");
                                    String novo_tipo = sc.nextLine();

                                    Usuario usuario_atualizado = new Usuario();
                                    usuario_atualizado.setId(id_usuario_atualizar);
                                    usuario_atualizado.setNome(novo_nome);
                                    usuario_atualizado.setEmail(novo_email);
                                    usuario_atualizado.setSenha(nova_senha);
                                    usuario_atualizado.setTipo(novo_tipo);

                                    usuarioDao.atualizarUsuario(usuario_atualizado);
                                } else {
                                    System.out.println("Opção inválida! Digite uma opção válida.");
                                }
                                break;

                            case "4", "deletarusuarios", "deletar", "d":
                                System.out.println("Qual ID do usuário que deseja deletar? ");
                                int id_usuario_deletar = sc.nextInt();
                                sc.nextLine();

                                Usuario usuario_deletar = usuarioDao.buscarPorId(id_usuario_deletar);
                                if(usuario_deletar == null){
                                    System.out.println("Nenhum usuário encontrado! ");
                                    break;
                                }

                                System.out.println("Nome: " + usuario_deletar.getNome() +
                                        "\nEmail: " + usuario_deletar.getEmail() +
                                        "\nTipo: " + usuario_deletar.getTipo() +
                                        "\nTem certeza que deseja deletar este usuário? (s/n)");
                                String deletar = sc.nextLine().toLowerCase().trim();

                                if (deletar.equals("s")) {
                                    usuarioDao.deletarUsuarioPorID(id_usuario_deletar);
                                } else {
                                    System.out.println("O usuário não foi deletado! ");
                                }
                                break;

                            case "5", "voltar", "v":
                                System.out.println("Voltando...");
                                break;

                            case "0", "sair", "s":
                                System.out.println("Saindo do sistema...");
                                break;

                            default:
                                System.out.println("Opção inválida! Escolha uma opção válida.");
                                break;
                        }
                    } while (!escolha.equalsIgnoreCase("0") && !escolha.equalsIgnoreCase("sair") && !escolha.equalsIgnoreCase("s"));

                case "3", "ingressos", "i":
                    do {
                        System.out.println("\n===============MENU INGRESSOS===============");
                        System.out.println("[1] - Adicionar Ingressos" +
                                "\n[2] - Ver ingressos" +
                                "\n[3] - Editar ingressos" +
                                "\n[4] - Deletar ingressos" +
                                "\n[5] - Voltar ao menu anterior" +
                                "\n[0] - Sair" +
                                "\nDigite o nome da coluna que gostaria de aplicar o filtro: ");

                        escolha = sc.nextLine().toLowerCase().trim();

                        switch (escolha){
                            case "1", "adicionaringressos", "adicionar":
                                System.out.println("Informe o nome do ingresso: ");
                                String nome = sc.nextLine();
                                System.out.println("Informe a descrição do ingresso: ");
                                String descricao = sc.nextLine();
                                System.out.println("Informe o preço: ");
                                double preco = sc.nextDouble();
                                sc.nextLine();

                                Ingresso novo_ingresso = new Ingresso();

                                novo_ingresso.setNome(nome);
                                novo_ingresso.setDescricao(descricao);
                                novo_ingresso.setPreco(preco);

                                ingressoDao.salvarIngresso(novo_ingresso);

                            case "2", "veringressos", "ver":
                                System.out.println("Gostaria de aplicar algum filtro? (s/n)");
                                String filtrar_ingresso = sc.nextLine().toLowerCase().trim();

                                if (filtrar_ingresso.equals("s")) {
                                    System.out.println("[1] - Nome" +
                                            "\n[2] - Descrição" +
                                            "\n[3] - Preço" +
                                            "\n[4] - Quantidade" +
                                            "\n[5] - Voltar ao menu anterior" +
                                            "\nDigite o nome da coluna que deseja aplicar o filtro: ");
                                    String coluna_filtro = sc.nextLine().toLowerCase().trim();
                                    String ingresso_filtro;

                                    switch (coluna_filtro) {
                                        case "nome", "descrição":
                                            System.out.println("Qual filtro?");
                                            ingresso_filtro = sc.nextLine().toLowerCase().trim();

                                            for(Ingresso i : ingressoDao.listarIngressos(coluna_filtro, ingresso_filtro, null)){
                                                if(i == null){
                                                    System.out.println("Operação inválida! Nenhum ingresso encontrado. ");
                                                    break;
                                                }
                                                System.out.println("\n----------Ingresso " + contador + "----------");
                                                System.out.println("ID: " + i.getId());
                                                System.out.println("Nome: " + i.getNome());
                                                System.out.println("Descrição: " + i.getDescricao());
                                                System.out.println("Preço: " + i.getPreco());
                                                System.out.println("Quantidade: " + i.getQuantidade());

                                                contador++;
                                            }
                                            break;

                                        case "preco":
                                            System.out.println("Qual preço?");
                                            double filtro_preco = sc.nextDouble();
                                            sc.nextLine();

                                            for(Ingresso i : ingressoDao.listarIngressos(coluna_filtro, null, filtro_preco)){
                                                if(i == null){
                                                    System.out.println("Operação inválida! Nenhum ingresso encontrado. ");
                                                    break;
                                                }
                                                System.out.println("\n----------Ingresso " + contador + "----------");
                                                System.out.println("ID: " + i.getId());
                                                System.out.println("Nome: " + i.getNome());
                                                System.out.println("Descrição: " + i.getDescricao());
                                                System.out.println("Preço: " + i.getPreco());
                                                System.out.println("Quantidade: " + i.getQuantidade());

                                                contador++;
                                            }
                                            break;

                                        case "voltar", "v":
                                            System.out.println("Voltando...");
                                            break;

                                        default:
                                            System.out.println("Opção inválida! Escolha uma opção válida.");
                                            break;
                                    }

                                } else {
                                    for(Ingresso i : ingressoDao.listarIngressos()){
                                        if(i == null){
                                            System.out.println("Operação inválida! Nenhum ingresso encontrado. ");
                                            break;
                                        }
                                        System.out.println("\n----------Ingresso " + contador + "----------");
                                        System.out.println("ID: " + i.getId());
                                        System.out.println("Nome: " + i.getNome());
                                        System.out.println("Descrição: " + i.getDescricao());
                                        System.out.println("Preço: " + i.getPreco());
                                        System.out.println("Quantidade: " + i.getQuantidade());

                                        contador++;
                                    }
                                }
                                break;

                            case "3", "atualizaringressos", "atualizar":
                                System.out.println("Qual ID do ingresso deseja atualizar? ");
                                int id_ingresso_atualizar = sc.nextInt();
                                sc.nextLine();

                                Ingresso ingresso_atualizar_existe = ingressoDao.buscarPorId(id_ingresso_atualizar);
                                if(ingresso_atualizar_existe == null){
                                    System.out.println("Operação inválida! Nenhum ingresso encontrado. ");
                                    break;
                                }

                                System.out.println("Gostaria de atualizar uma ou mais informações? (uma/varias)");
                                String quant_informacoes = sc.nextLine().toLowerCase().trim();

                                if (quant_informacoes.equals("uma") || quant_informacoes.equals("u")) {
                                    System.out.println("[1] - Nome" +
                                            "\n[2] - Descrição" +
                                            "\n[3] - Preço" +
                                            "\n[4] - Voltar ao menu anterior" +
                                            "\nDigite o nome da coluna que deseja fazer a atualização: ");

                                    String coluna = sc.nextLine().toLowerCase().trim();
                                    String informacao;

                                    switch (coluna) {
                                        case "nome", "descricao":
                                            System.out.println("Qual informação?");
                                            informacao = sc.nextLine();
                                            ingressoDao.atualizarIngresso(id_ingresso_atualizar, coluna, informacao, null);
                                            break;

                                        case "preco":
                                            System.out.println("Qual preço?");
                                            double novo_preco = sc.nextDouble();
                                            sc.nextLine();
                                            ingressoDao.atualizarIngresso(id_ingresso_atualizar, coluna, null, novo_preco);
                                            break;

                                        case "voltar", "v":
                                            System.out.println("Voltando...");
                                            break;

                                        default:
                                            System.out.println("Opção inválida! Escolha uma opção válida.");
                                            break;
                                    }
                                } else if (quant_informacoes.equals("varias") || quant_informacoes.equals("v")) {
                                    System.out.println("Informe o novo nome: ");
                                    String novo_nome = sc.nextLine();
                                    System.out.println("Informe a nova descrição: ");
                                    String nova_descricao = sc.nextLine();
                                    System.out.println("Informe o novo preço: ");
                                    double novo_preco = sc.nextDouble();
                                    sc.nextLine();

                                    Ingresso ingresso_atualizado = new Ingresso();
                                    ingresso_atualizado.setId(id_ingresso_atualizar);
                                    ingresso_atualizado.setNome(novo_nome);
                                    ingresso_atualizado.setDescricao(nova_descricao);
                                    ingresso_atualizado.setPreco(novo_preco);

                                    ingressoDao.atualizarIngresso(ingresso_atualizado);
                                } else {
                                    System.out.println("Opção inválida! Digite uma opção válida.");
                                }
                                break;

                            case "4", "deletaringressos", "deletar", "d":
                                System.out.println("Qual ID do ingresso que deseja deletar? ");
                                int id_ingresso_deletar = sc.nextInt();
                                sc.nextLine();

                                Ingresso ingresso_deletar = ingressoDao.buscarPorId(id_ingresso_deletar);
                                if(ingresso_deletar == null){
                                    System.out.println("Operação inválida! Nenhum ingresso encontrado. ");
                                    break;
                                }

                                System.out.println("Nome: " + ingresso_deletar.getNome() +
                                        "\nDescrição: " + ingresso_deletar.getDescricao() +
                                        "\nPreço: " + ingresso_deletar.getPreco() +
                                        "\nTem certeza que deseja deletar este ingresoso? (s/n)");
                                String deletar = sc.nextLine().toLowerCase().trim();

                                if (deletar.equals("s")) {
                                    usuarioDao.deletarUsuarioPorID(id_ingresso_deletar);
                                } else {
                                    System.out.println("O ingresso não foi deletado! ");
                                }
                                break;

                            case "5", "voltar", "v":
                                System.out.println("Voltando...");
                                break;

                            case "0", "sair", "s":
                                System.out.println("Saindo do sistema...");
                                break;

                            default:
                                System.out.println("Opção inválida! Escolha uma opção válida.");
                                break;

                        }
                    } while (!escolha.equalsIgnoreCase("0") && !escolha.equalsIgnoreCase("sair") && !escolha.equalsIgnoreCase("s"));
                case "4", "compras", "c":
                    do {
                        System.out.println("\n===============MENU COMPRAS===============");
                        System.out.println("[1] - Ver compras" +
                                "\n[2] - Deletar compras" +
                                "\n[3] - Voltar ao menu anterior" +
                                "\n[0] - Sair" +
                                "\nEscolha uma das opções acima: ");

                        escolha = sc.nextLine().toLowerCase().trim();

                        switch (escolha){
                            case "1", "vercompras", "ver":
                                System.out.println("Gostaria de aplicar algum filtro? (s/n)");
                                String filtrar_compra = sc.nextLine().toLowerCase().trim();

                                if (filtrar_compra.equals("s")) {
                                    System.out.println("[1] - ID" +
                                            "\n[2] - ID Cliente" +
                                            "\n[3] - ID Ingresso" +
                                            "\n[4] - Voltar ao menu anterior" +
                                            "\nDigite o nome da coluna que deseja aplicar o filtro: ");
                                    String coluna_filtro = sc.nextLine().toLowerCase().trim();
                                    int compra_filtro;

                                    switch (coluna_filtro) {
                                        case "id":
                                            System.out.println("Qual ID?");
                                            compra_filtro = sc.nextInt();
                                            sc.nextLine();

                                            Compra c = compraDao.buscarPorId(compra_filtro);
                                            if(c == null){
                                                System.out.println("Operação inválida! Nenhuma compra ingresso encontrado. ");
                                                break;
                                            }

                                            Usuario nome_usuario = usuarioDao.buscarPorId(c.getId_usuario());
                                            Ingresso nome_ingresso = ingressoDao.buscarPorId(c.getId_ingresso());

                                            System.out.println("\n----------Ingresso----------");
                                            System.out.println("ID: " + c.getId());
                                            System.out.println("ID Cliente: " + c.getId_usuario());
                                            System.out.println("Nome cliente: " + nome_usuario.getNome());
                                            System.out.println("ID Ingresso: " + c.getId_ingresso());
                                            System.out.println("Nome ingresso: " + nome_ingresso.getNome());
                                            System.out.println("Quantidade: " + c.getQuantidade());
                                            System.out.println("Valor: " + c.getValor());
                                            break;

                                        case "idcliente", "cliente", "idingresso", "ingresso":
                                            System.out.println("Qual ID?");
                                            int filtro_id = sc.nextInt();
                                            sc.nextLine();

                                            for(Compra d : compraDao.buscarPorId(coluna_filtro, filtro_id)){
                                                if(d == null){
                                                    System.out.println("Operação inválida! Nenhuma compra encontrado. ");
                                                    break;
                                                }

                                                Usuario nome_usuario2 = usuarioDao.buscarPorId(d.getId_usuario());
                                                Ingresso nome_ingresso2 = ingressoDao.buscarPorId(d.getId_ingresso());

                                                System.out.println("\n----------Ingresso----------");
                                                System.out.println("ID: " + d.getId());
                                                System.out.println("ID Cliente: " + d.getId_usuario());
                                                System.out.println("Nome cliente: " + nome_usuario2.getNome());
                                                System.out.println("ID Ingresso: " + d.getId_ingresso());
                                                System.out.println("Nome ingresso: " + nome_ingresso2.getNome());
                                                System.out.println("Quantidade: " + d.getQuantidade());
                                                System.out.println("Valor: " + d.getValor());

                                                contador++;
                                            }
                                            break;

                                        case "voltar", "v":
                                            System.out.println("Voltando...");
                                            break;

                                        default:
                                            System.out.println("Opção inválida! Escolha uma opção válida.");
                                            break;
                                    }

                                } else {
                                    for(Compra c : compraDao.listarCompras()){
                                        if(c == null){
                                            System.out.println("Operação inválida! Nenhuma compra encontrado. ");
                                            break;
                                        }
                                        Usuario nome_usuario = usuarioDao.buscarPorId(c.getId_usuario());
                                        Ingresso nome_ingresso = ingressoDao.buscarPorId(c.getId_ingresso());

                                        System.out.println("\n----------Ingresso----------");
                                        System.out.println("ID: " + c.getId());
                                        System.out.println("ID Cliente: " + c.getId_usuario());
                                        System.out.println("Nome cliente: " + nome_usuario.getNome());
                                        System.out.println("ID Ingresso: " + c.getId_ingresso());
                                        System.out.println("Nome ingresso: " + nome_ingresso.getNome());
                                        System.out.println("Quantidade: " + c.getQuantidade());
                                        System.out.println("Valor: " + c.getValor());

                                        contador++;
                                    }
                                }
                                break;

                            case "2", "deletarcompras", "deletar", "d":
                                System.out.println("Qual ID da compra que você quer deletar? ");
                                int id_compra_deletar = sc.nextInt();
                                sc.nextLine();

                                Compra compra_deletar = compraDao.buscarPorId(id_compra_deletar);
                                if(compra_deletar == null){
                                    System.out.println("Operação inválida! Nenhuma compra encontrado. ");
                                    break;
                                }

                                Usuario nome_usuario = usuarioDao.buscarPorId(compra_deletar.getId_usuario());
                                Ingresso nome_ingresso = ingressoDao.buscarPorId(compra_deletar.getId_ingresso());

                                System.out.println("\n----------Ingresso----------");
                                System.out.println("ID: " + compra_deletar.getId());
                                System.out.println("ID Cliente: " + compra_deletar.getId_usuario());
                                System.out.println("Nome cliente: " + nome_usuario.getNome());
                                System.out.println("ID Ingresso: " + compra_deletar.getId_ingresso());
                                System.out.println("Nome ingresso: " + nome_ingresso.getNome());
                                System.out.println("Quantidade: " + compra_deletar.getQuantidade());
                                System.out.println("Valor: " + compra_deletar.getValor());
                                String deletar = sc.nextLine().toLowerCase().trim();

                                if (deletar.equals("s")) {
                                    animalDao.deletarAnimalPorId(id_compra_deletar);
                                } else {
                                    System.out.println("A compra não foi deletada! ");
                                }
                                break;

                            case "3", "voltar", "v":
                                System.out.println("Voltando...");
                                break;

                            case "0", "sair", "s":
                                System.out.println("Saindo do sistema...");
                                break;

                            default:
                                System.out.println("Opção inválida! Escolha uma opção válida.");
                                break;

                        }
                    } while (!escolha.equalsIgnoreCase("0") && !escolha.equalsIgnoreCase("sair") && !escolha.equalsIgnoreCase("s"));

                case "5", "voltar", "v":
                    System.out.println("Voltando...");
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

    sc.close();
}