package org.itas.core.bytecode;

import org.itas.core.GameObject;
import org.itas.core.annotation.SQLEntity;

@SQLEntity("model")
public class ModelTest extends GameObject {

	protected ModelTest(String Id) {
		super(Id);
	}

	private String name;
	private char sex;
	private boolean isMarry;
	private byte chirldCount;
	private short gemCount;
	private int age;
	private long hp;
	private float gameCoin;
	private double money;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getGemCount() {
		return gemCount;
	}

	public void setGemCount(short gemCount) {
		this.gemCount = gemCount;
	}

	public long getHp() {
		return hp;
	}

	public void setHp(long hp) {
		this.hp = hp;
	}

	public float getGameCoin() {
		return gameCoin;
	}

	public void setGameCoin(float gameCoin) {
		this.gameCoin = gameCoin;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public byte getChirldCount() {
		return chirldCount;
	}

	public void setChirldCount(byte chirldCount) {
		this.chirldCount = chirldCount;
	}

	public boolean isMarry() {
		return isMarry;
	}

	public void setMarry(boolean isMarry) {
		this.isMarry = isMarry;
	}

	@Override
	protected String PRIFEX() {
		return "mt_";
	}

	@Override
	protected <T extends GameObject> T autoInstance(String Id) {
		return null;
	}

}
