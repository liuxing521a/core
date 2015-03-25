package org.itas.core;

import org.itas.core.bytecode.BooleanProviderTest;
import org.itas.core.bytecode.ByteProviderTest;
import org.itas.core.bytecode.CharProviderTest;
import org.itas.core.bytecode.DoubleProviderTest;
import org.itas.core.bytecode.FloatProviderTest;
import org.itas.core.bytecode.IntProviderTest;
import org.itas.core.bytecode.LongProviderTest;
import org.itas.core.bytecode.ShortProviderTest;
import org.itas.core.bytecode.StringProviderTest;
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
        ShortProviderTest.class,
        IntProviderTest.class,
        LongProviderTest.class,
        DoubleProviderTest.class,
        FloatProviderTest.class,
        StringProviderTest.class,
//        TestFieldResourcesProvider.class,
//        TestFieldSimpleProvider.class,
//        TestFieldListProvider.class,
//        TestFieldSetProvider.class,
//        TestFieldTimestampCode.class,
//        TestFieldEnumByteProvider.class,
//        TestFieldEnumIntProvider.class,
//        TestFieldEnumStringProvider.class,
//        TestFieldMapProvider.class,
        
//        TestMethodSQLProvider.class,
//        TestMethodColneProvider.class,
//        TestByteCodes.class,
//        DataContainersTest.class,
})
public class TestAll {
	
}
