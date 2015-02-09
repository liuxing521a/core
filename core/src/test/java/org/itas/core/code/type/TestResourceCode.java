package org.itas.core.code.type;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;
import net.itas.core.resource.Resource;

import org.itas.core.code.Modify;
import org.junit.Before;
import org.junit.Test;

public class TestResourceCode {

	private SourceCode codeType;
	
	@Before
	public void setUP() {
		codeType = new SourceCode(new Modify() {
			@Override
			protected String toModify() {
				return null;
			}
		});
	}
	
	@Test
	public void testSetStatement() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get("org.itas.core.code.type.TestSimpleCode$Model");
		CtField field = clazz.getDeclaredField("bs");
		
		String expected = "\t\tString rid_bs = \"\";" + 
						"\n\t\tif (getBs() != null) {" +
						"\n\t\t\trid_bs = getBs().getId();" + 
						"\n\t\t}" + 
						"\n\t\tstate.setString(1, rid_bs);";
		
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = "\t\tString rid_bs = result.getString(\"bs\");" +
					"\n\t\tif (org.itas.util.Utils.Objects.nonEmpty(rid_bs)) {" +
						"\n\t\t\tsetBs(org.itas.core.Pool.getResource(rid_bs));" +
					"\n\t\t}";
		content = codeType.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	
	
	class Model {
		
		private HeroRes bs;

		public HeroRes getBs() {
			return bs;
		}

		public void setBs(HeroRes bs) {
			this.bs = bs;
		}
		
	}
	
	private class HeroRes extends Resource {

		protected HeroRes(String Id) {
			super(Id);
		}


	}
	
}
