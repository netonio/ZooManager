package dao;

import model.Ingresso;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IngressoDAO {
    public void salvarIngresso(Ingresso ingresso) {

        String sql = "INSERT INTO ingressos (nome, descricao, preco, quantidade) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            // Cria uma conexão com banco de dados
            conn = ConnectionFactory.conectar();

            //Cria uma PreparedStatement, para executar uma query
            pstm = conn.prepareStatement(sql);

            //Adiciona os valores que são esperados pela query
            pstm.setString(1, ingresso.getNome());
            pstm.setString(2, ingresso.getDescricao());
            pstm.setDouble(3, ingresso.getPreco());
            pstm.setInt(4, ingresso.getQuantidade());

            //Executa a query
            pstm.execute();

            System.out.println("Ingresso adicionado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public List<Ingresso> listarIngressos(){

        String sql = "SELECT * FROM ingressos";

        List<Ingresso> ingressos = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstm = null;

        //Classe que recupera os dados do banco
        ResultSet rset = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            rset = pstm.executeQuery();

            while(rset.next()){
                Ingresso ingresso = new Ingresso();

                // Recupera ID
                ingresso.setId(rset.getInt("id"));
                // Recupera nome
                ingresso.setNome(rset.getString("nome"));
                // Recupera descrição
                ingresso.setDescricao(rset.getString("descricao"));
                // Recupera preço
                ingresso.setPreco(rset.getDouble("preco"));
                // Recupera quantidade
                ingresso.setQuantidade(rset.getInt("quantidade"));

                ingressos.add(ingresso);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharConexao(conn, pstm, rset);
        }

        return ingressos;
    }

    public List<Ingresso> listarIngressos(String coluna, String filtro_palavra, Double filtro_preco) throws IllegalArgumentException {

        Set<String> colunasValidas = Set.of("nome", "descricao", "preco");

        if (!colunasValidas.contains(coluna.toLowerCase())){
            throw new IllegalArgumentException("Coluna inválida! ");
        }

        String sql = "SELECT * FROM ingressos WHERE " + coluna + " = ?";

        List<Ingresso> ingressos = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstm = null;

        // Classe que recupera os dados do banco
        ResultSet rset = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            switch (coluna.toLowerCase()) {
                case "nome", "descricao":
                    if (filtro_palavra != null && !filtro_palavra.isEmpty()) {
                        pstm.setString(1, filtro_palavra);
                    } else {
                        throw new IllegalArgumentException("Filtro não pode ser vazio! ");
                    }
                    break;
                case "preco":
                    if (filtro_preco != 0) {
                        pstm.setDouble(1, filtro_preco);
                    } else {
                        throw new IllegalArgumentException("Filtro não pode ser vazio! ");
                    }
                    break;
            }
            rset = pstm.executeQuery();

            while(rset.next()) {
                Ingresso ingresso = new Ingresso();

                // Recupera ID
                ingresso.setId(rset.getInt("id"));
                // Recupera nome
                ingresso.setNome(rset.getString("nome"));
                // Recupera descricao
                ingresso.setDescricao(rset.getString("descricao"));
                // Recupera preco
                ingresso.setPreco(rset.getDouble("preco"));
                // Recupera quantidade
                ingresso.setQuantidade(rset.getInt("quantidade"));

                ingressos.add(ingresso);
            }
        } catch (Exception e){
            e.printStackTrace();

        } finally {
            ConnectionFactory.fecharConexao(conn, pstm, rset);
        }
        return ingressos;
    }

    public Ingresso buscarPorId(int id){

        String sql = "SELECT * FROM ingressos WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        Ingresso ingresso = null;

        try {
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, id);

            rset = pstm.executeQuery();

            if(rset.next()){
                ingresso = new Ingresso();
                ingresso.setId(rset.getInt("id"));
                ingresso.setNome(rset.getString("nome"));
                ingresso.setDescricao(rset.getString("descricao"));
                ingresso.setPreco(rset.getDouble("preco"));
                ingresso.setQuantidade(rset.getInt("quantidade"));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharConexao(conn, pstm, rset);
        }
        return ingresso;
    }

    public void atualizarIngresso(Ingresso ingresso){

        String sql = "UPDATE ingressos SET nome = ?, descricao = ?, preco = ?, quantidade = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, ingresso.getNome());
            pstm.setString(2, ingresso.getDescricao());
            pstm.setDouble(3, ingresso.getPreco());
            pstm.setInt(4, ingresso.getQuantidade());

            pstm.executeUpdate();

            System.out.println("Ingresso atualizado com sucesso!");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharConexao(conn, pstm);
        }
    }

    public void atualizarIngresso(int id, String coluna, String string_atualizada, Double preco_atualizado) throws IllegalArgumentException {

        Set<String> colunasValidas = Set.of("nome", "descricao", "preco", "quantidade");

        if (!colunasValidas.contains(coluna.toLowerCase())){
            throw new IllegalArgumentException("Coluna inválida! ");
        }

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        switch(coluna.toLowerCase()){
            case "nome", "descricao":
                if (string_atualizada == null || string_atualizada.isBlank()) {
                    throw new IllegalArgumentException("O valor para " + coluna + " não pode ser vazio! ");
                }
                break;

            case "preco":
                if (preco_atualizado == null || preco_atualizado < 0) {
                    throw new IllegalArgumentException("Preço inválido! ");
                }
                break;
        }

        String sql = "UPDATE ingressos SET " + coluna + " = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            switch(coluna.toLowerCase()){
                case "nome", "descricao":
                    pstm.setString(1, string_atualizada);
                    break;
                case "preco":
                    pstm.setDouble(1, preco_atualizado);
                    break;
            }
            pstm.setInt(2, id);

            pstm.executeUpdate();

            System.out.println("Ingresso atualizado com sucesso!");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharConexao(conn, pstm);
        }
    }

    public void deletarIngressoPorId(int id){

        String sql = "DELETE FROM ingressos WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, id);

            pstm.executeUpdate();

            System.out.println("Ingresso deletado com sucesso!");

        } catch(Exception e){
            e.printStackTrace();

        } finally {
            ConnectionFactory.fecharConexao(conn, pstm);
        }
    }
}
