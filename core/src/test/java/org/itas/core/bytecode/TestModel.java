package org.itas.core.bytecode;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import net.itas.core.annotation.SQLEntity;

import org.itas.core.GameObject;
import org.itas.core.Index;
import org.itas.core.Unique;

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

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		
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
	
}
