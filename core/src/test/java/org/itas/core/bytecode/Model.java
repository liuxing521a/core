package org.itas.core.bytecode;

import java.sql.Timestamp;

import org.itas.core.EnumByte;
import org.itas.core.EnumInt;
import org.itas.core.EnumString;
import org.itas.core.GameObject;
import org.itas.core.Simple;
import org.itas.core.annotation.SQLEntity;

@SQLEntity("model")
public class Model extends GameObject {

	public enum SexType implements EnumByte {
		man {
			@Override
			public byte key() {
				return 1;
			}
		},
	}
	
	public enum Effect implements EnumInt {
		effect1{
			@Override
			public int key() {
				return 2;
			}
		},
	}
	
	public enum SkillType implements EnumString {
		skill1{
			@Override
			public String key() {
				return "skill";
			}
		},
	}
	
	public enum HeroType {
		hero
	}
	
	protected Model(String Id) {
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
	private SexType sexType;
	private Effect effect;
	private SkillType skillType;
	private HeroType heroType;
	private Simple<Hero> heroS;
	private Timestamp updateAt;
	
	
	public Timestamp getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}

	public Simple<Hero> getHeroS() {
		return heroS;
	}

	public void setHeroS(Simple<Hero> heroS) {
		this.heroS = heroS;
	}

	public HeroType getHeroType() {
		return heroType;
	}

	public void setHeroType(HeroType heroType) {
		this.heroType = heroType;
	}

	public SkillType getSkillType() {
		return skillType;
	}

	public void setSkillType(SkillType skillType) {
		this.skillType = skillType;
	}

	public Effect getEffect() {
		return effect;
	}

	public void setEffect(Effect effect) {
		this.effect = effect;
	}

	public SexType getSexType() {
		return sexType;
	}

	public void setSexType(SexType sexType) {
		this.sexType = sexType;
	}

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
