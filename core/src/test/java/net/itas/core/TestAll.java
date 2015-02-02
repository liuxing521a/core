package net.itas.core;

import net.itas.core.io.rmi.TestRemote;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
//指定运行器
@Suite.SuiteClasses({ 
	TestByteCodes.class, 
	TestRemote.class, 
	})
public class TestAll {
	
}
