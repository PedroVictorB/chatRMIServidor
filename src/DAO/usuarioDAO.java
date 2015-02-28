/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Entidades.Usuario;
import Entidades.UsuarioLogado;
import bd.conexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Pedro
 */
public class usuarioDAO {
    public Connection connection;

    public usuarioDAO() {
        this.connection = new conexaoDB().getConnection();
    }
    
    
    public boolean cadastrar(Usuario c) {
        String sql = "INSERT INTO usuario(nome, login, senha) VALUES(?,?,?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getLogin());
            stmt.setString(3, c.getSenha());
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException u) {
            return false;
        }

    }
    
    public UsuarioLogado buscarUsuario(UsuarioLogado u) {
        String sql = "SELECT * FROM usuario WHERE login = ?";

        ResultSet rs;
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, u.getLogin());
            rs = stmt.executeQuery();
            while(rs.next()){
                u.setId((int)rs.getInt("id"));
                u.setNome((String)rs.getString("nome"));
            }
            stmt.close();
        } catch (SQLException ex) {
            return null;
        }
        return u;
    }
    
    public Boolean login(String login, String senha){
        String sql = "SELECT * FROM usuario WHERE login = ?";

        ResultSet rs;
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, login);
            rs = stmt.executeQuery();
            while(rs.next()){
                if(!senha.equals((String)rs.getString("senha"))){
                    return false;
                }
            }
            stmt.close();
        } catch (SQLException ex) {
            return null;
        }
        return true;
    }
}
