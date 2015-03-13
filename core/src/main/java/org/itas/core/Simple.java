package org.itas.core;

/**
 *<p>简单对象</p>
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014年3月24日
 */
public final class Simple<T extends GameObject> implements HashId {
	
  private final String Id;
	
  private final String prefix;
  
  private String name;
	
  public Simple(String Id) {
	this(Id, "");
  }

  public Simple(String Id, String name) {
	this.prefix = avilidPrefix(Id);
	this.Id = Id;
	this.name = name;
  }
	
  @Override
  public String getId() {
	return Id;
  }
	
  public String getName() {
	return name;
  }

  public void setName(String name) {
	this.name = name;
  }

  public T enty() {
	return Pool.get(Id);
  }
	
  public String prefix() {
	return prefix;
  }

  @Override
  public boolean equals(Object data) {
	if (data == this) {
	  return true;
	}
		
	if (!(data instanceof Simple)) {
	  return false;
	}

	return Id.equals(((Simple<?>)data).Id);
  }
	
  @Override
  public int hashCode() {
	return 31 + Id.hashCode();
  }
	
  @Override
  public String toString() {
	return String.format("[Id=%s, name=%s]", Id, name);
  }

  private String avilidPrefix(String Id) {
	if (Id == null || Id.length() < 3) {
	  throw new IllnessException("PRIFEX must endwith [_]," + Id);
	}
			
	String prifex = Id.substring(0, 3);
	if (prifex.charAt(2) != '_') {
	  throw new IllegalArgumentException("PRIFEX must endwith [_]");
	}

	return prifex;
  }
	
}
