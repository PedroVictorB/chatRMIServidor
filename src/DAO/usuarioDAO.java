/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Entidades.Usuario;
import bd.conexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        String sql = "INSERT INTO clientes(nome, login, senha) VALUES(?,?,?)";

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
}
