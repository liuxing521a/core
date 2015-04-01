package org.itas.core.bytecode;

import org.itas.core.GameObjectNoCache;

public class Item extends GameObjectNoCache {

	protected Item(String Id) {
		super(Id);
	}

	@Override
	public Item clone(String Id) {
		return new Item(Id);
	}
	
	@Override
	public String PRIFEX() {
		return "it_";
	}

}
