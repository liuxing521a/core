package org.itas.core;

import org.itas.core.code.TestCodeType;
import org.itas.core.code.type.TestBoolCode;
import org.itas.core.code.type.TestByteCode;
import org.itas.core.code.type.TestCharCode;
import org.itas.core.code.type.TestDoubleCode;
import org.itas.core.code.type.TestFloatCode;
import org.itas.core.code.type.TestIntCode;
import org.itas.core.code.type.TestLongCode;
import org.itas.core.code.type.TestResourceCode;
import org.itas.core.code.type.TestShortCode;
import org.itas.core.code.type.TestSimpleCode;
import org.itas.core.code.type.TestStringCode;
import org.itas.core.code.type.TestTimestampCode;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
//指定运行器
@Suite.SuiteClasses({ 
	TestByteCodes.class, 
	TestCodeType.class, 
	TestBoolCode.class, 
	TestByteCode.class, 
	TestCharCode.class, 
	TestDoubleCode.class, 
	TestFloatCode.class, 
	TestIntCode.class, 
	TestLongCode.class, 
	TestResourceCode.class, 
	TestShortCode.class, 
	TestStringCode.class, 
	TestSimpleCode.class, 
	TestTimestampCode.class, 
	})
public class TestAll {
	
}
