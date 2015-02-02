package net.itas.core.io.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.itas.core.annotation.RmiRef;

@RmiRef(TestExample.class)
public class TestExampleImple extends UnicastRemoteObject implements TestExample {
	
	private static final long serialVersionUID = 6157032960326961993L;

	public TestExampleImple() throws RemoteException {
		super();
	}

	@Override
	public boolean isConnected() throws RemoteException {
		return true;
	}

	@Override
	public String test(String value)throws RemoteException {
		return "回复:" + value;
	}

}
