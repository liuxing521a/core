package org.itas.core;

import org.itas.core.bytecode.TestCodeBooleanProvider;
import org.itas.core.bytecode.TestCodeByteProvider;
import org.itas.core.bytecode.TestCodeCharProvider;
import org.itas.core.bytecode.TestCodeDoubleProvider;
import org.itas.core.bytecode.TestCodeEnumByteProvider;
import org.itas.core.bytecode.TestCodeEnumIntProvider;
import org.itas.core.bytecode.TestCodeEnumStringProvider;
import org.itas.core.bytecode.TestCodeFloatProvider;
import org.itas.core.bytecode.TestCodeIntProvider;
import org.itas.core.bytecode.TestCodeListProvider;
import org.itas.core.bytecode.TestCodeLongProvider;
import org.itas.core.bytecode.TestCodeResourcesProvider;
import org.itas.core.bytecode.TestCodeSetProvider;
import org.itas.core.bytecode.TestCodeShortProvider;
import org.itas.core.bytecode.TestCodeSimpleProvider;
import org.itas.core.bytecode.TestCodeStringProvider;
import org.itas.core.bytecode.TestCodeTimestampCode;
import org.itas.core.bytecode.TestCodeType;
import org.itas.core.util.TestType;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
//指定运行器
@Suite.SuiteClasses({ 
	TestByteCodes.class, 
	TestCodeType.class, 
	TestType.class, 
	TestCodeBooleanProvider.class, 
	TestCodeByteProvider.class, 
	TestCodeCharProvider.class, 
	TestCodeDoubleProvider.class, 
	TestCodeFloatProvider.class, 
	TestCodeIntProvider.class, 
	TestCodeLongProvider.class, 
	TestCodeResourcesProvider.class, 
	TestCodeShortProvider.class, 
	TestCodeStringProvider.class, 
	TestCodeSimpleProvider.class, 
	TestCodeListProvider.class, 
	TestCodeSetProvider.class, 
	TestCodeTimestampCode.class, 
	TestCodeEnumByteProvider.class, 
	TestCodeEnumIntProvider.class, 
	TestCodeEnumStringProvider.class, 
	})
public class TestAll {
	
}
