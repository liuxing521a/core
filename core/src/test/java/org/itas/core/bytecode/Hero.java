package org.itas.core.bytecode;

import java.util.LinkedHashSet;
import java.util.Set;

import org.itas.core.GameObjectAotuID;
import org.itas.core.Simple;
import org.itas.core.annotation.Clazz;
import org.itas.core.annotation.Size;
import org.itas.core.bytecode.Model.Effect;
import org.itas.core.bytecode.Model.HeroType;
import org.itas.core.bytecode.Model.SexType;
import org.itas.core.bytecode.Model.SkillType;

public class Hero extends GameObjectAotuID {

	protected Hero(String Id) {
		super(Id);
	}
	
	@Clazz(LinkedHashSet.class)
	private Set<Integer> points;
	private Set<Simple<Hero>> depotS;
	@Size(16)
	private Set<HeroRes> heroResList;
	private Set<HeroType> heroTypeList;
	private Set<SexType> sexTypeList;
	private Set<Effect> effectTypeList;
	private Set<SkillType> skillTypeList;
	
	public Set<Integer> getPoints() {
		return points;
	}

	public void setPoints(Set<Integer> points) {
		this.points = points;
	}

	public Set<Simple<Hero>> getDepotS() {
		return depotS;
	}

	public void setDepotS(Set<Simple<Hero>> depotS) {
		this.depotS = depotS;
	}

	public Set<HeroRes> getHeroResList() {
		return heroResList;
	}

	public void setHeroResList(Set<HeroRes> heroResList) {
		this.heroResList = heroResList;
	}

	public Set<HeroType> getHeroTypeList() {
		return heroTypeList;
	}

	public void setHeroTypeList(Set<HeroType> heroTypeList) {
		this.heroTypeList = heroTypeList;
	}

	public Set<SexType> getSexTypeList() {
		return sexTypeList;
	}

	public void setSexTypeList(Set<SexType> sexTypeList) {
		this.sexTypeList = sexTypeList;
	}

	public Set<Effect> getEffectTypeList() {
		return effectTypeList;
	}

	public void setEffectTypeList(Set<Effect> effectTypeList) {
		this.effectTypeList = effectTypeList;
	}

	public Set<SkillType> getSkillTypeList() {
		return skillTypeList;
	}

	public void setSkillTypeList(Set<SkillType> skillTypeList) {
		this.skillTypeList = skillTypeList;
	}

	@Override
	protected String PRIFEX() {
		return null;
	}

}
