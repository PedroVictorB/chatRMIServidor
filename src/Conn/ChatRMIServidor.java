/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Conn;

import DAO.usuarioDAO;
import Entidades.Usuario;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro
 */
public class ChatRMIServidor extends UnicastRemoteObject implements Comandos{
    
    ChatRMIServidor obj;
    Registry r;
    
    public ChatRMIServidor()throws RemoteException{
        super();
    }
    
    public void startServer(){
        try { // Creates an object of the HelloServer class. 
            obj = new ChatRMIServidor(); // Bind this object instance to the name "HelloServer". 
            r = LocateRegistry.createRegistry(1099);
            r.bind("host", obj);
            //Naming.rebind("sendMessage", obj);
            System.out.println("Ligado no registro");
        } catch (Exception ex) {
            System.out.println("error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public Boolean Login(String login, String senha) throws RemoteException {
        try {
            System.out.println("Nome: "+login);
            System.out.println("Senha: "+senha);
            System.out.println(UnicastRemoteObject.getClientHost());
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ChatRMIServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public Boolean Cadastrar(String nome, String login, String senha) throws RemoteException {
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setLogin(login);
        u.setSenha(senha);
        return new usuarioDAO().cadastrar(u);
    }
    
}