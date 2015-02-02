package net.itas.core.io.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Rmi extends Remote {

	boolean isConnected() throws RemoteException;
	
}
