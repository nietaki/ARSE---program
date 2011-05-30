package transmission;

import java.io.IOException;

import session.Listener;
import session.Message;

public class MessageHandler implements IncommingMessageHandler {
	
	private Listener listener;
	
	public MessageHandler(Listener listener){
		this.listener = listener;
	}

	@Override
	public Boolean acceptsClassHash(String classHash) {
		if(classHash.equals(StaticHelper.getClassnameHash(Message.class))){
			System.out.println("Message hash matches");
			return true;
		}
		return false;
	}

	@Override
	public void handleIncommingMessage(IncommingMessageContainer msg) {
		try {
			Message message = msg.getIncomingObject(Message.class);
			listener.handle(message, msg.getSenderId(), msg.getDateReceived());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
