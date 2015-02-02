package net.itas.core.io.rmi;

import java.rmi.RemoteException;

import net.itas.core.io.rmi.Rmi;


public interface TestExample extends Rmi {
	
	public String test(String value) throws RemoteException;
	
}
