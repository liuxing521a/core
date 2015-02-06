package net.itas.core.dispatch;

import org.itas.core.net.nio.Message;

import net.itas.core.Dispatch;
import net.itas.core.User;

public class XmlDispatch extends Dispatch {

	@Override
	public void bind(String pack) throws Exception {
		
	}

	@Override
	public void unBind() {
		
	}

	@Override
	protected Handle getHandle(short clazzHead) {
		return null;
	}

	@Override
	protected void dispatchEvent(User user, Handle event, Message message) throws Exception {
		
	}

}
