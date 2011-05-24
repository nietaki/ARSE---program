package session;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PlayerSession {
	private Display display;
	private boolean loggedIn;
	
	protected PlayerSession(Display display) {
		this.display = display;
		this.loggedIn = false;
	}

	protected void login(String username, String address, Integer port) throws LoginErrorException{
		if(username.isEmpty()){
			throw new LoginErrorException("Podaj swój nick");
		}
		if(address.isEmpty()){
			throw new LoginErrorException("Podaj adres serwera");
		}
		if(port == null){
			throw new LoginErrorException("Podaj numer portu");
		}
		// TODO logowanie
		loggedIn = true;
	}
	
	protected void logout(){
		// TODO
		loggedIn = false;
	}

	protected void open() {
		// TODO Auto-generated method stub
	}

	protected boolean loggedIn() {
		return loggedIn;
	}

}
