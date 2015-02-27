/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Pedro
 */
public class conexaoDB {
    String serverName = "127.0.0.1";    //caminho do servidor do BD
    String mydatabase = "chat";        //nome do seu banco de dados
    String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
    String username = "root";        //nome de um usuário de seu BD      
    String password = "admin";      //sua senha de acesso
    
    public java.sql.Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        }
        catch(SQLException excecao) {
            throw new RuntimeException(excecao);
        }
    }
    
}
