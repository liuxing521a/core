package org.itas.core;

import com.google.inject.Binder;


public interface Service {

	interface OnStarUP extends Service {
		
		abstract void onStartUP() throws Exception;

	}

	interface OnShutdown extends Service {
		
		abstract void onShutdown() throws Exception;

	}
	
	interface OnBinder {
		
		abstract void bind(Binder binder);
		
	}
		
}
