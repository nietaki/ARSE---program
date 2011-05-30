package session;

import java.util.Date;

public abstract class Listener {
	public abstract void handle(Object o, String senderId, Date dateRecieved);
}
