package transmission;

import java.io.IOException;

import session.Listener;
import session.Logout;

public class LogoutHandler implements IncommingMessageHandler {

	private Listener listener;
	
	public LogoutHandler(Listener listener){
		this.listener = listener;
	}

	@Override
	public Boolean acceptsClassHash(String classHash) {
		if(classHash.equals(StaticHelper.getClassnameHash(Logout.class))){
			System.out.println("Message hash matches");
			return true;
		}
		return false;
	}

	@Override
	public void handleIncommingMessage(IncommingMessageContainer msg) {
		try {
			Logout logout = msg.getIncomingObject(Logout.class);
			listener.handle(logout, msg.getSenderId(), msg.getDateReceived(), msg.getFromAddress());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
