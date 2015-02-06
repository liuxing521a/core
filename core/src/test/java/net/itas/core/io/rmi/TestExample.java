package net.itas.core.io.rmi;

import java.rmi.RemoteException;

import org.itas.core.net.rmi.Rmi;


public interface TestExample extends Rmi {
	
	public String test(String value) throws RemoteException;
	
}
