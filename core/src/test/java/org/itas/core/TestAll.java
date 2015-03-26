package org.itas.core;

import org.itas.core.bytecode.BooleanProviderTest;
import org.itas.core.bytecode.ByteProviderTest;
import org.itas.core.bytecode.CharProviderTest;
import org.itas.core.bytecode.DoubleProviderTest;
import org.itas.core.bytecode.EnumByteProviderTest;
import org.itas.core.bytecode.EnumIntProviderTest;
import org.itas.core.bytecode.EnumProviderTest;
import org.itas.core.bytecode.EnumStringProviderTest;
import org.itas.core.bytecode.FloatProviderTest;
import org.itas.core.bytecode.IntProviderTest;
import org.itas.core.bytecode.LongProviderTest;
import org.itas.core.bytecode.ShortProviderTest;
import org.itas.core.bytecode.SimpleProviderTest;
import org.itas.core.bytecode.StringProviderTest;
import org.itas.core.bytecode.TimestampProviderTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
//指定运行器
@Suite.SuiteClasses({
//        TestCodeType.class,
//        TestType.class,
		
        BooleanProviderTest.class,
        ByteProviderTest.class,
        CharProviderTest.class,
        DoubleProviderTest.class,
        EnumByteProviderTest.class,
        EnumIntProviderTest.class,
        EnumProviderTest.class,
        EnumStringProviderTest.class,
        FloatProviderTest.class,
        IntProviderTest.class,
        LongProviderTest.class,
        ShortProviderTest.class,
        SimpleProviderTest.class,
        StringProviderTest.class,
        TimestampProviderTest.class,
//        TestFieldResourcesProvider.class,
//        TestFieldSimpleProvider.class,
//        TestFieldListProvider.class,
//        TestFieldSetProvider.class,
//        TestFieldMapProvider.class,
        
//        TestMethodSQLProvider.class,
//        TestMethodColneProvider.class,
//        TestByteCodes.class,
//        DataContainersTest.class,
})
public class TestAll {
	
}
