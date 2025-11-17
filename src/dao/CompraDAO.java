package dao;

import model.Compra;
import model.Ingresso;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CompraDAO {

    public void salvarCompra(Compra compra) {

        String sql = "INSERT INTO compras (id_usuario, id_ingresso, quantidade) VALUES (?, ?, ?)";

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

            //Executa a query
            pstm.execute();

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

    public Compra buscarPorId(Integer id){

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

            rset = pstm.executeQuery(sql);

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
