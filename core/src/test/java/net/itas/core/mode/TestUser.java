package net.itas.core.mode;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.Descriptor;
import net.itas.core.GameObject;
import net.itas.core.Simple;
import net.itas.core.annotation.Clazz;
import net.itas.core.annotation.SQLEntity;
import net.itas.core.resource.Resource;

@SQLEntity("test_user")
public class TestUser extends GameObject {

	protected TestUser(String Id) {
		super(Id);
	}

	private int userId;

	private byte sexValue;

	private short age;

	private boolean isTest;

	private char sexName;

	private long money;

	private String name;

	private Set<Simple<TestUser>> kaob;
	
	private List<Simple<TestUser>> childS;

	@Clazz(LinkedHashSet.class)
	private Set<Simple<TestUser>> kaob1;

	@Clazz(LinkedList.class)
	private List<Simple<TestUser>> childS1;

	private Map<String, Simple<TestUser>> child;

	private Resource res;
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public byte getSexValue() {
		return sexValue;
	}

	public void setSexValue(byte sexValue) {
		this.sexValue = sexValue;
	}

	public short getAge() {
		return age;
	}

	public void setAge(short age) {
		this.age = age;
	}

	public boolean isTest() {
		return isTest;
	}

	public void setTest(boolean isTest) {
		this.isTest = isTest;
	}

	public char getSexName() {
		return sexName;
	}

	public void setSexName(char sexName) {
		this.sexName = sexName;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Simple<TestUser>> getKaob() {
		return kaob;
	}

	public void setKaob(Set<Simple<TestUser>> kaob) {
		this.kaob = kaob;
	}

	public List<Simple<TestUser>> getChildS() {
		return childS;
	}

	public void setChildS(List<Simple<TestUser>> childS) {
		this.childS = childS;
	}

	public Set<Simple<TestUser>> getKaob1() {
		return kaob1;
	}

	public void setKaob1(Set<Simple<TestUser>> kaob1) {
		this.kaob1 = kaob1;
	}

	public List<Simple<TestUser>> getChildS1() {
		return childS1;
	}

	public void setChildS1(List<Simple<TestUser>> childS1) {
		this.childS1 = childS1;
	}

	public Map<String, Simple<TestUser>> getChild() {
		return child;
	}

	public void setChild(Map<String, Simple<TestUser>> child) {
		this.child = child;
	}

	public Resource getRes() {
		return res;
	}

	public void setRes(Resource res) {
		this.res = res;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String PRIFEX() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected <T extends GameObject> T autoInstance(String Id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get("net.itas.core.mode.TestUser");
		CtMethod[] methods = clazz.getMethods();
		for (CtMethod method : methods) {
			if ("setName".equals(method.getName())) {
				method.insertAfter("this.modify(\"" + method.getName() + "\");");
				method.insertAfter("this.modify(\"" + method.getName() + "\");");
				method.insertAfter("this.modify(\"" + method.getName() + "\");");
				method.insertAfter("this.modify(\"" + method.getName() + "\");");
			}
			System.out.println(method.getName());
		}
		
		clazz.writeFile("D:/");
	}
}
