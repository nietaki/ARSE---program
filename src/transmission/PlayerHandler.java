package transmission;

import java.io.IOException;

import session.Listener;
import session.Player;

public class PlayerHandler implements IncommingMessageHandler {
	
	private Listener listener;
	
	public PlayerHandler(Listener listener){
		this.listener = listener;
	}

	@Override
	public Boolean acceptsClassHash(String classHash) {
		if(classHash.equals(StaticHelper.getClassnameHash(Player.class))){
			System.out.println("Player hash matches");
			return true;
		}
		return false;
	}

	@Override
	public void handleIncommingMessage(IncommingMessageContainer msg) {
		try {
			Player player = msg.getIncomingObject(Player.class);
			listener.handle(player, msg.getSenderId(), msg.getDateReceived());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
