/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Conn;

import Entidades.Mensagem;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Pedro
 */
interface ReceiveMessage extends Remote{
    public void mensagemGrupo(String msg) throws RemoteException;
    public void mensagemIndividual(Mensagem m) throws RemoteException;
}
