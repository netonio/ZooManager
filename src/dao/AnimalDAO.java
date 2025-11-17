package dao;

import model.Animal;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AnimalDAO {

    public void salvarAnimal(Animal animal) {

        String sql = "INSERT INTO animais (nome, categoria, especie, idade, habitat, alimentacao, descricao) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            // Cria uma conexão com banco de dados
            conn = ConnectionFactory.conectar();

            //Cria uma PreparedStatement, para executar uma query
            pstm = conn.prepareStatement(sql);

            //Adiciona os valores que são esperados pela query
            pstm.setString(1, animal.getNome());
            pstm.setString(2, animal.getCategoria());
            pstm.setString(3, animal.getEspecie());
            pstm.setInt(4, animal.getIdade());
            pstm.setString(5, animal.getHabitat());
            pstm.setString(6, animal.getAlimentacao());
            pstm.setString(7, animal.getDescricao());

            //Executa a query
            pstm.execute();

            System.out.println("Animal adicionado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            ConnectionFactory.fecharConexao(conn, pstm);
        }
    }

    public List<Animal> listarAnimais(){

        String sql = "SELECT * FROM animais";

        List<Animal> animais = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstm = null;

        //Classe que recupera os dados do banco
        ResultSet rset = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            rset = pstm.executeQuery();

            while(rset.next()){
                Animal animal = new Animal();

                // Recupera ID
                animal.setId(rset.getInt("id"));
                // Recupera nome
                animal.setNome(rset.getString("nome"));
                // Recupera categoria
                animal.setCategoria(rset.getString("categoria"));
                // Recupera especie
                animal.setEspecie(rset.getString("especie"));
                // Recupera idade
                animal.setIdade(rset.getInt("id"));
                // Recupera habitat
                animal.setHabitat(rset.getString("habitat"));
                // Recupera alimentacao
                animal.setAlimentacao(rset.getString("alimentacao"));
                // Recupera descrição
                animal.setDescricao(rset.getString("descricao"));

                animais.add(animal);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharConexao(conn, pstm, rset);
        }

        return animais;
    }

    public List<Animal> listarAnimais(String coluna, String filtro_palavra) throws IllegalArgumentException {

        Set<String> colunasValidas = Set.of("nome", "descricao", "categoria", "especie", "habitat", "alimentacao");

        if (!colunasValidas.contains(coluna.toLowerCase())) {
            throw new IllegalArgumentException("Operação invalida! Coluna inválida. ");
        }

        if (filtro_palavra == null || filtro_palavra.isBlank()) {
            throw new IllegalArgumentException("Filtro não pode ser vazio! ");
        }

        String sql = "SELECT * FROM animais WHERE " + coluna + " = ?";

        List<Animal> animais = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstm = null;

        // Classe que recupera os dados do banco
        ResultSet rset = null;

        try {
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            rset = pstm.executeQuery();

            while (rset.next()) {
                Animal animal = new Animal();

                // Recupera ID
                animal.setId(rset.getInt("id"));
                // Recupera nome
                animal.setNome(rset.getString("nome"));
                // Recupera categoria
                animal.setCategoria(rset.getString("categoria"));
                // Recupera especie
                animal.setEspecie(rset.getString("especie"));
                // Recupera idade
                animal.setIdade(rset.getInt("id"));
                // Recupera habitat
                animal.setHabitat(rset.getString("habitat"));
                // Recupera alimentacao
                animal.setAlimentacao(rset.getString("alimentacao"));
                // Recupera descrição
                animal.setDescricao(rset.getString("descricao"));

                animais.add(animal);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            ConnectionFactory.fecharConexao(conn, pstm, rset);
        }

        return animais;
    }

    public Animal buscarPorId(Integer id) throws IllegalArgumentException {

        if(id == null || id <= 0){
            throw new IllegalArgumentException("Operação invalida! ID não pode ser nulo ou menor que zero");
        }

        String sql = "SELECT * FROM animais WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        Animal animal = null;

        try {
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, id);

            rset = pstm.executeQuery();

            if(rset.next()){
                animal = new Animal();
                // Recupera ID
                animal.setId(rset.getInt("id"));
                // Recupera nome
                animal.setNome(rset.getString("nome"));
                // Recupera categoria
                animal.setCategoria(rset.getString("categoria"));
                // Recupera especie
                animal.setEspecie(rset.getString("especie"));
                // Recupera idade
                animal.setIdade(rset.getInt("idade"));
                // Recupera habitat
                animal.setHabitat(rset.getString("habitat"));
                // Recupera alimentacao
                animal.setAlimentacao(rset.getString("alimentacao"));
                // Recupera descrição
                animal.setDescricao(rset.getString("descricao"));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharConexao(conn, pstm, rset);
        }
        return animal;
    }

    public void atualizarAnimal(Animal animal){

        String sql = "UPDATE animais SET nome = ?, categoria = ?, especie = ?, idade = ?, habitat = ?, alimentacao = ?, descricao = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, animal.getNome());
            pstm.setString(2, animal.getCategoria());
            pstm.setString(3, animal.getEspecie());
            pstm.setInt(4, animal.getIdade());
            pstm.setString(5, animal.getHabitat());
            pstm.setString(6, animal.getAlimentacao());
            pstm.setString(7, animal.getDescricao());

            pstm.executeUpdate();

            System.out.println("Animal atualizado com sucesso!");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharConexao(conn, pstm);
        }
    }

    public void atualizarAnimal(int id, String coluna, String string_atualizada, Integer idade_atualizada) throws IllegalArgumentException {

        Set<String> colunasValidas = Set.of("nome", "categoria", "especie", "habitat", "alimentacao", "descricao");

        if (!colunasValidas.contains(coluna.toLowerCase())){
            throw new IllegalArgumentException("Coluna inválida! ");
        }

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        switch(coluna.toLowerCase()){
            case "nome", "categoria", "especie", "habitat", "alimentacao", "descricao":
                if (string_atualizada == null || string_atualizada.isBlank()) {
                    throw new IllegalArgumentException("O valor para " + coluna + " não pode ser vazio! ");
                }
                break;

            case "idade":
                if (idade_atualizada == null || idade_atualizada < 0) {
                    throw new IllegalArgumentException("Idade inválida! ");
                }
                break;
        }

        String sql = "UPDATE animais SET " + coluna + " = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            switch(coluna.toLowerCase()){
                case "nome", "categoria", "especie", "habitat", "alimentacao", "descricao":
                    pstm.setString(1, string_atualizada);
                    break;
                case "idade":
                    pstm.setInt(1, idade_atualizada);
                    break;
            }
            pstm.setInt(2, id);

            pstm.executeUpdate();

            System.out.println("Animal atualizado com sucesso!");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharConexao(conn, pstm);
        }
    }

    public void deletarAnimalPorId(int id){

        String sql = "DELETE FROM animais WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, id);

            pstm.executeUpdate();

            System.out.println("Animal deletado com sucesso!");

        } catch(Exception e){
            e.printStackTrace();

        } finally {
            ConnectionFactory.fecharConexao(conn, pstm);
        }
    }}


