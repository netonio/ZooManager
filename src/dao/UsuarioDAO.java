package dao;

import model.Usuario;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UsuarioDAO {
    public void salvarUsuario(Usuario usuario) {

        String sql = "INSERT INTO usuarios (nome, email, senha, tipo) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            // Cria uma conexão com banco de dados
            conn = ConnectionFactory.conectar();

            //Cria uma PreparedStatement, para executar uma query
            pstm = conn.prepareStatement(sql);

            //Adiciona os valores que são esperados pela query
            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getEmail());
            pstm.setString(3, usuario.getSenha());
            pstm.setString(4, usuario.getTipo());

            //Executa a query
            pstm.executeUpdate();

            System.out.println("Usuário salvo com sucesso! ");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            ConnectionFactory.fecharConexao(conn, pstm);
        }
    }

    public List<Usuario> listarUsuarios(){

        String sql = "SELECT * FROM usuarios";

        List<Usuario> usuarios = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstm = null;

        // Classe que recupera os dados do banco
        ResultSet rset = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            rset = pstm.executeQuery();

            while(rset.next()){
                Usuario usuario = new Usuario();

                // Recupera ID
                usuario.setId(rset.getInt("id"));
                // Recupera nome
                usuario.setNome(rset.getString("nome"));
                // Recupera email
                usuario.setEmail(rset.getString("email"));
                // Recupera senha
                usuario.setSenha(rset.getString("senha"));
                // Recupera tipo
                usuario.setTipo(rset.getString("tipo"));

                usuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharConexao(conn, pstm, rset);
        }

        return usuarios;
    }

    public List<Usuario> listarUsuarios(String coluna, String filtro_palavra) throws IllegalArgumentException {

        Set<String> colunasValidas = Set.of("nome", "tipo");

        if (!colunasValidas.contains(coluna.toLowerCase())) {
            throw new IllegalArgumentException("Coluna inválida! ");
        }

        if (filtro_palavra == null || filtro_palavra.isBlank()) {
            throw new IllegalArgumentException("Filtro não pode ser vazio! ");
        }

        String sql = "SELECT * FROM usuarios WHERE " + coluna + " = ?";

        List<Usuario> usuarios = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstm = null;

        // Classe que recupera os dados do banco
        ResultSet rset = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, filtro_palavra);

            rset = pstm.executeQuery();

            while(rset.next()){
                Usuario usuario = new Usuario();

                // Recupera ID
                usuario.setId(rset.getInt("id"));
                // Recupera nome
                usuario.setNome(rset.getString("nome"));
                // Recupera email
                usuario.setEmail(rset.getString("email"));
                // Recupera senha
                usuario.setSenha(rset.getString("senha"));
                // Recupera tipo
                usuario.setTipo(rset.getString("tipo"));

                usuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            ConnectionFactory.fecharConexao(conn, pstm, rset);
        }
        return usuarios;
    }

    public Usuario buscarPorId(int id){

        String sql = "SELECT * FROM usuarios WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        Usuario usuario = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, id);

            rset = pstm.executeQuery();

            if (rset.next()){
                usuario = new Usuario();
                usuario.setId(rset.getInt("id"));
                usuario.setNome(rset.getString("nome"));
                usuario.setEmail(rset.getString("email"));
                usuario.setSenha(rset.getString("senha"));
                usuario.setTipo(rset.getString("tipo"));
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            ConnectionFactory.fecharConexao(conn, pstm, rset);
        }
        return usuario;
    }

    public Usuario buscarPorEmail(String email){

        String sql = "SELECT * FROM usuarios WHERE email = ?";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        Usuario usuario = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, email);

            rset = pstm.executeQuery();

            if (rset.next()){
                usuario = new Usuario();
                usuario.setId(rset.getInt("id"));
                usuario.setNome(rset.getString("nome"));
                usuario.setEmail(rset.getString("email"));
                usuario.setSenha(rset.getString("senha"));
                usuario.setTipo(rset.getString("tipo"));
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            ConnectionFactory.fecharConexao(conn, pstm, rset);
        }
        return usuario;
    }

    public void atualizarUsuario(Usuario usuario){

        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ?, tipo = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getEmail());
            pstm.setString(3, usuario.getSenha());
            pstm.setString(4, usuario.getTipo());
            pstm.setInt(5, usuario.getId());

            pstm.executeUpdate();

            System.out.println("Usuário atualizado com sucesso!");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConnectionFactory.fecharConexao(conn, pstm);
        }
    }

    public void atualizarUsuario(int id, String coluna, String string_atualizada)  throws IllegalArgumentException {

        Set<String> colunasValidas = Set.of("nome", "email", "senha", "tipo");

        if (!colunasValidas.contains(coluna.toLowerCase())) {
            throw new IllegalArgumentException("Coluna inválida! ");
        }

        if (string_atualizada == null || string_atualizada.isBlank()) {
            throw new IllegalArgumentException("O valor atualizado não pode ser nulo ou vazio");
        }

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        String sql = "UPDATE usuarios SET " + coluna + " = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, string_atualizada);
            pstm.setInt(2, id);

            pstm.executeUpdate();

            System.out.println("Usuário atualizado com sucesso!");

        } catch (Exception e){
            e.printStackTrace();

        } finally {
            ConnectionFactory.fecharConexao(conn, pstm);
        }
    }

    public void deletarUsuarioPorID(int id){

        String sql = "DELETE FROM usuarios WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = ConnectionFactory.conectar();
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, id);

            pstm.executeUpdate();

            System.out.println("Usuário deletado com sucesso!");

        } catch(Exception e){
            e.printStackTrace();

        } finally {
            ConnectionFactory.fecharConexao(conn, pstm);
        }
    }

    public Usuario validarUsuario(String email, String senha){

        Usuario usuario_encontrado = buscarPorEmail(email);

        if (usuario_encontrado == null){
            return null;
        } else {
            if(usuario_encontrado.getSenha().equals(senha)){
                return usuario_encontrado;
            } else {
                return null;
            }
        }
    }
}