package dao;

import model.*;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CompraDAO {

    public void salvarCompra(Compra compra) {

        String sql = "INSERT INTO compras (id_usuario, id_ingresso, quantidade, valor) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            // Cria uma conexão com banco de dados
            conn = ConnectionFactory.conectar();

            //Cria uma PreparedStatement, para executar uma query
            pstm = conn.prepareStatement(sql);

            //Adiciona os valores que são esperados pela query
            pstm.setInt(1,compra.getId_usuario());
            pstm.setInt(2,compra.getId_ingresso());
            pstm.setInt(3, compra.getQuantidade());
            pstm.setDouble(4, compra.getValor());

            //Executa a query
            pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharConexao(conn, pstm);
        }
    }

    public List<Compra> listarCompras(){

        String sql = "SELECT * FROM compras";

        List<Compra> compras = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstm = null;

        //Classe que recupera os dados do banco
        ResultSet rset = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            rset = pstm.executeQuery();

            while(rset.next()){
                Compra compra = new Compra();

                // Recupera ID
                compra.setId(rset.getInt("id"));
                // Recupera ID do usuario
                compra.setId_usuario(rset.getInt("id_usuario"));
                // Recupera ID do ingresso
                compra.setId_ingresso(rset.getInt("id_ingresso"));
                // Recupera Quantidade
                compra.setQuantidade(rset.getInt("quantidade"));

                compras.add(compra);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharConexao(conn, pstm, rset);
        }

        return compras;
    }

    public Compra buscarPorId(Integer id) throws IllegalArgumentException {

        if(id == null || id <= 0){
            throw new IllegalArgumentException("Operação invalida! ID não pode ser nulo ou menor que zero");
        }

        String sql = "SELECT * FROM compras WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        Compra compra = null;

        try {
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, id);

            rset = pstm.executeQuery();

            if(rset.next()){
                compra = new Compra();
                compra.setId(rset.getInt("id"));
                compra.setId_usuario(rset.getInt("id_usuario"));
                compra.setId_ingresso(rset.getInt("id_ingresso"));
                compra.setQuantidade(rset.getInt("quantidade"));

            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharConexao(conn, pstm, rset);
        }
        return compra;
    }

    public List<Compra> buscarPorId(String coluna, Integer filtro_id) throws IllegalArgumentException {

        Set<String> colunasValidas = Set.of("idusuario", "idingresso", "id_usuario", "id_ingresso");

        if (!colunasValidas.contains(coluna)) {
            throw new IllegalArgumentException("Operação inválida! Coluna de busca inválida: " + coluna);
        }

        if(filtro_id == null || filtro_id <= 0){
            throw new IllegalArgumentException("Operação inválida! ID não pode ser nulo ou menor que zero.");
        }

        String colunaCerta = coluna.equals("idusuario") || coluna.equals("id_usuario") ? "id_usuario" : "id_ingresso";
        String sql = "SELECT * FROM compras WHERE " + colunaCerta + " = ?";

        List<Compra> compras = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            // 3. Setar o parâmetro
            pstm.setInt(1, filtro_id);

            // 4. Executar a query SEM ARGUMENTOS
            rset = pstm.executeQuery();

            while (rset.next()) {
                Compra compra = new Compra();
                compra.setId(rset.getInt("id"));
                compra.setId_usuario(rset.getInt("id_usuario"));
                compra.setId_ingresso(rset.getInt("id_ingresso"));
                compra.setQuantidade(rset.getInt("quantidade"));
                compra.setValor(rset.getDouble("valor"));

                compras.add(compra);
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharConexao(conn, pstm, rset);
        }
        return compras;
    }

    public void deletarCompraPorId(int id){

        String sql = "DELETE FROM compras WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, id);

            pstm.executeUpdate();

            System.out.println("Compra deletada com sucesso!");

        } catch(Exception e){
            e.printStackTrace();

        } finally {
            ConnectionFactory.fecharConexao(conn, pstm);
        }
    }
}
