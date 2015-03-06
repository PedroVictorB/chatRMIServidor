/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Conn;

import Entidades.UsuarioLogado;
import Entidades.teste;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Pedro
 */
public interface Comandos extends Remote{
    public Boolean Login(String login, String senha) throws RemoteException;
    public Boolean Cadastrar(String nome, String login, String senha) throws RemoteException;
    public teste ListaDeClientesConectados() throws RemoteException;
    public void SendMessage(String msg) throws RemoteException;
}
