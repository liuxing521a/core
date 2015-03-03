package org.itas.core.bytecode;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.itas.core.GameObject;
import org.itas.core.annotation.Index;
import org.itas.core.annotation.SQLEntity;
import org.itas.core.annotation.Unique;
import org.itas.core.resource.Resource;

@SQLEntity("model")
public class TestModel extends GameObject {

	protected TestModel(String Id) {
		super(Id);
	}
	
	private String name;
	
	@Unique
	private String identy;
	
	@Index
	private String address;
	
	private List<String> test;
	
	private Map<String, Resource> test1;
	
	private Set<Integer> test2;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdenty() {
		return identy;
	}

	public void setIdenty(String identy) {
		this.identy = identy;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getTest() {
		return test;
	}

	public void setTest(List<String> test) {
		this.test = test;
	}

	public Map<String, Resource> getTest1() {
		return test1;
	}

	public void setTest1(Map<String, Resource> test1) {
		this.test1 = test1;
	}

	public Set<Integer> getTest2() {
		return test2;
	}

	public void setTest2(Set<Integer> test2) {
		this.test2 = test2;
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
	
}
