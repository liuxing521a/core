package org.itas.core.bytecode;

import org.itas.core.util.FirstCharTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
//指定运行器
@Suite.SuiteClasses({
	FirstCharTest.class,
  BooleanProviderTest.class,
  ByteProviderTest.class,
  CharProviderTest.class,
  DoubleArrayProviderTest.class,
  DoubleProviderTest.class,
  EnumByteProviderTest.class,
  EnumIntProviderTest.class,
  EnumProviderTest.class,
  EnumStringProviderTest.class,
  FloatProviderTest.class,
  GameObjectProviderTest.class,
  GameObjectAutoIdProviderTest.class,
  GameObjectNoCacheProviderTest.class,
  IntProviderTest.class,
  ListProviderTest.class,
  LongProviderTest.class,
  MapProviderTest.class,
  ResourceProviderTest.class,
  SetProviderTest.class,
  SingleArrayProviderTest.class,
  ShortProviderTest.class,
  SimpleProviderTest.class,
  StringProviderTest.class,
  TimestampProviderTest.class,
})
public class ByteCodeTest {
	
}
