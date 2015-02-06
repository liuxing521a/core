package net.itas.core.io.rmi;

import java.util.Arrays;

import org.itas.core.net.Hosts.Address;
import org.itas.core.net.rmi.RmiClient;
import org.itas.core.net.rmi.RmiServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestRemote {

	private RmiClient client;
	
	private Address address;
	
	@Before
	public void setUp() throws Exception {
		RmiServer server = RmiServer.getDefault();
		address = new Address("127.0.0.1", 8182);
		String packName = "net.itas.core.io.rmi";
		server.Initializer(address, packName);
		
		client = RmiClient.getDefault();
		client.Initializer(address, Arrays.asList(TestExample.class));
	}
	
	@Test
	public void doTest() throws Exception  {
		TestExample exp = (TestExample)client.lookUp(address, TestExample.class);
		Assert.assertTrue(exp.isConnected());
	}

}
