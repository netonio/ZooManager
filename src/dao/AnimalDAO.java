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

}
