package org.itas.core;

import org.itas.core.bytecode.TestCodeType;
import org.itas.core.bytecode.TestStatementBooleanProvider;
import org.itas.core.bytecode.TestStatementByteProvider;
import org.itas.core.bytecode.TestStatementCharProvider;
import org.itas.core.bytecode.TestStatementDoubleProvider;
import org.itas.core.bytecode.TestStatementFloatProvider;
import org.itas.core.bytecode.TestStatementIntProvider;
import org.itas.core.bytecode.TestStatementListProvider;
import org.itas.core.bytecode.TestStatementLongProvider;
import org.itas.core.bytecode.TestStatementResourcesProvider;
import org.itas.core.bytecode.TestStatementShortProvider;
import org.itas.core.bytecode.TestStatementSimpleProvider;
import org.itas.core.bytecode.TestStatementStringProvider;
import org.itas.core.bytecode.TestTimestampCode;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
//指定运行器
@Suite.SuiteClasses({ 
	TestByteCodes.class, 
	TestCodeType.class, 
	TestStatementBooleanProvider.class, 
	TestStatementByteProvider.class, 
	TestStatementCharProvider.class, 
	TestStatementDoubleProvider.class, 
	TestStatementFloatProvider.class, 
	TestStatementIntProvider.class, 
	TestStatementLongProvider.class, 
	TestStatementResourcesProvider.class, 
	TestStatementShortProvider.class, 
	TestStatementStringProvider.class, 
	TestStatementSimpleProvider.class, 
	TestStatementListProvider.class, 
	TestTimestampCode.class, 
	})
public class TestAll {
	
}
