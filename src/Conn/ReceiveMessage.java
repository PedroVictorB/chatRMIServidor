/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Conn;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Pedro
 */
interface ReceiveMessage extends Remote{
    public void mensagem(String msg) throws RemoteException;
}
