package net.itas.core;

@FunctionalInterface
public interface CallBack<T> {

	public void called(T data);
	
}
