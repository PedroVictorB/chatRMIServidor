/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Conn;

import Entidades.UsuarioLogado;
import Entidades.ListaUsuariosLogados;
import Entidades.Mensagem;
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
    public ListaUsuariosLogados ListaDeClientesConectados() throws RemoteException;
    public void SendMessageGrupo(String msg) throws RemoteException;
    public void SendMessageIndividual(Mensagem m) throws RemoteException;
    public void Deslogar(String login) throws RemoteException;
    public UsuarioLogado buscarUsuarioByName(String login) throws RemoteException;
    public UsuarioLogado buscarUsuarioById(int id) throws RemoteException;
}
