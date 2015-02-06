package org.itas.core.net.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Rmi extends Remote {

	boolean isConnected() throws RemoteException;
	
}
