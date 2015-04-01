package org.itas.core;

import org.itas.core.bytecode.ByteCodeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
//指定运行器
@Suite.SuiteClasses({
//        TestCodeType.class,
//        TestType.class,
		
	ByteCodeTest.class,
        
//        TestMethodSQLProvider.class,
//        TestMethodColneProvider.class,
//        TestByteCodes.class,
//        DataContainersTest.class,
})
public class TestAll {
	
}
