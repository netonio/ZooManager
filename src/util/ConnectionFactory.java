package util;

import java.sql.*;

public class ConnectionFactory {

    // Nome de usuário do banco
    private static final String USERNAME = "root";
    // Senha do usuário do banco
    private static final String PASSWORD = "BancodeDados123";
    // Caminho para acessar o banco
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/ZooManager";

    // Cria a conexão com o banco de dados
    public static Connection conectar() throws SQLException{
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    };

    public static void fecharConexao(Connection conn, PreparedStatement pstm, ResultSet rset){
        try{
            if (rset != null) conn.close();
            if (pstm != null) pstm.close();
            if (conn != null) conn.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void fecharConexao(Connection conn, PreparedStatement pstm){
        fecharConexao(conn, pstm, null);
    }

}
