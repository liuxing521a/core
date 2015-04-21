package org.itas.core.resources;

import org.itas.core.Builder;
import org.itas.core.Service.OnBinder;
import org.itas.core.Service.OnShutdown;
import org.itas.core.Service.OnStarUP;

import com.google.inject.Binder;

public class XmlManager implements OnShutdown, OnStarUP, OnBinder {

	@Override
	public void onStartUP() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShutdown() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bind(Binder binder) {
		// TODO Auto-generated method stub
		
	}
	
	public static class XmlManagerBuilder implements Builder {

		@Override
		public XmlManager builder() {
			return null;
		}
		
	}

}
