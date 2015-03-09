/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conn;

import DAO.usuarioDAO;
import Entidades.Usuario;
import Entidades.UsuarioLogado;
import Entidades.ListaUsuariosLogados;
import Entidades.Mensagem;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro
 */
public class ChatRMIServidor extends UnicastRemoteObject implements Comandos {

    ChatRMIServidor obj;
    Registry r;
    ArrayList<UsuarioLogado> lista = new ArrayList<UsuarioLogado>();

    public ChatRMIServidor() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        ChatRMIServidor chat;

        try {
            chat = new ChatRMIServidor();
            chat.startServer();
        } catch (RemoteException ex) {
            Logger.getLogger(ChatRMIServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startServer() {
        try {
            obj = new ChatRMIServidor();
            r = LocateRegistry.createRegistry(1099);
            r.bind("host", obj);
            System.out.println("Ligado no registro");
        } catch (Exception ex) {
            System.out.println("error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public Boolean Login(String login, String senha) throws RemoteException {
        try {
            if (new usuarioDAO().login(login, senha)) {
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).getLogin().equals(login)) {
                        return false;
                    }
                }
                UsuarioLogado l = new usuarioDAO().buscarUsuario(new UsuarioLogado(login));
                l.setIp(UnicastRemoteObject.getClientHost());
                System.out.println("Usuário Logado --> Login:" + l.getLogin() + " Nome:" + l.getNome() + " ID:" + l.getId() + " IP:" + l.getIp());
                lista.add(l);//coloca o usuario em uma lista de usuarios logados
                return true;
            }
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(ChatRMIServidor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return false;
    }

    @Override
    public Boolean Cadastrar(String nome, String login, String senha) throws RemoteException {
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setLogin(login);
        u.setSenha(senha);
        return new usuarioDAO().cadastrar(u);
    }

    @Override
    public ListaUsuariosLogados ListaDeClientesConectados() throws RemoteException {
        ListaUsuariosLogados t = new ListaUsuariosLogados();//array list colocada em uma classe para permitir transporte rmi
        t.lista = this.lista;
        return t;
    }

    @Override
    public void Deslogar(String login) throws RemoteException {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getLogin().equals(login)) {
                System.out.println("Usuario " + lista.get(i).getLogin() + " deslogado.");
                lista.remove(i);
                try {
                    r = LocateRegistry.getRegistry();
                    r.unbind(login);
                } catch (NotBoundException ex) {
                    //Logger.getLogger(ChatRMIServidor.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Registro do usuário "+login+" não encontrado");
                } catch (AccessException ex) {
                    //Logger.getLogger(ChatRMIServidor.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Acesso ao usuário "+login+" não foi permitido.");
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    @Override
    public UsuarioLogado buscarUsuarioById(int id) throws RemoteException {
        return new usuarioDAO().buscarUsuarioID(new UsuarioLogado(id));
    }

    @Override
    public UsuarioLogado buscarUsuarioByName(String login) throws RemoteException {
        return new usuarioDAO().buscarUsuario(new UsuarioLogado(login));
    }

    @Override
    public void SendMessageGrupo(String msg) throws RemoteException {
        for (UsuarioLogado u : lista) {
            try {
                ReceiveMessage m = (ReceiveMessage) Naming.lookup("//" + u.getIp() + "/" + u.getLogin());
                System.out.println("Mensagem: " + msg);
                m.mensagemGrupo(msg);
            } catch (NotBoundException ex) {
                Logger.getLogger(ChatRMIServidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(ChatRMIServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void SendMessageIndividual(Mensagem m) throws RemoteException {
        for (UsuarioLogado u : lista) {
            if (m.getTo().equals(u.getLogin())) {
                try {
                    ReceiveMessage rm = (ReceiveMessage) Naming.lookup("//" + u.getIp() + "/" + u.getLogin());
                    rm.mensagemIndividual(m);
                } catch (NotBoundException ex) {
                    Logger.getLogger(ChatRMIServidor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ChatRMIServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
