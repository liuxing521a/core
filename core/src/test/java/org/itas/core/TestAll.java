package org.itas.core;

import org.itas.core.bytecode.TestByteCodes;
import org.itas.core.bytecode.TestCodeType;
import org.itas.core.bytecode.TestFieldBooleanProvider;
import org.itas.core.bytecode.TestFieldByteProvider;
import org.itas.core.bytecode.TestFieldCharProvider;
import org.itas.core.bytecode.TestFieldDoubleProvider;
import org.itas.core.bytecode.TestFieldEnumByteProvider;
import org.itas.core.bytecode.TestFieldEnumIntProvider;
import org.itas.core.bytecode.TestFieldEnumStringProvider;
import org.itas.core.bytecode.TestFieldFloatProvider;
import org.itas.core.bytecode.TestFieldIntProvider;
import org.itas.core.bytecode.TestFieldListProvider;
import org.itas.core.bytecode.TestFieldLongProvider;
import org.itas.core.bytecode.TestFieldMapProvider;
import org.itas.core.bytecode.TestFieldResourcesProvider;
import org.itas.core.bytecode.TestFieldSetProvider;
import org.itas.core.bytecode.TestFieldShortProvider;
import org.itas.core.bytecode.TestFieldSimpleProvider;
import org.itas.core.bytecode.TestFieldStringProvider;
import org.itas.core.bytecode.TestFieldTimestampCode;
import org.itas.core.bytecode.TestMethodColneProvider;
import org.itas.core.bytecode.TestMethodSQLProvider;
import org.itas.core.bytecode.TestType;
import org.itas.core.util.DataContainersTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
//指定运行器
@Suite.SuiteClasses(value = {
        TestCodeType.class,
        TestType.class,
        TestFieldBooleanProvider.class,
        TestFieldByteProvider.class,
        TestFieldCharProvider.class,
        TestFieldDoubleProvider.class,
        TestFieldFloatProvider.class,
        TestFieldIntProvider.class,
        TestFieldLongProvider.class,
        TestFieldResourcesProvider.class,
        TestFieldShortProvider.class,
        TestFieldStringProvider.class,
        TestFieldSimpleProvider.class,
        TestFieldListProvider.class,
        TestFieldSetProvider.class,
        TestFieldTimestampCode.class,
        TestFieldEnumByteProvider.class,
        TestFieldEnumIntProvider.class,
        TestFieldEnumStringProvider.class,
        TestFieldMapProvider.class,
        TestMethodSQLProvider.class,
        TestMethodColneProvider.class,
        TestByteCodes.class,
        DataContainersTest.class,
})
public class TestAll {
	
}
