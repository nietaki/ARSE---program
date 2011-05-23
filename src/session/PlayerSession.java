package session;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PlayerSession {
	private Display display;
	
	protected PlayerSession(Display display) {
		this.display = display;
	}

	protected void login(String username, String address, String port) throws LoginErrorException{
		if(username.isEmpty()){
			throw new LoginErrorException("Podaj swój nick");
		}
		if(address.isEmpty()){
			throw new LoginErrorException("Podaj adres serwera");
		}
		if(port.isEmpty()){
			throw new LoginErrorException("Podaj numer portu");
		}
		// TODO logowanie
	}
	
	protected void logout(){
		// TODO
	}

	protected void open() {
		// TODO Auto-generated method stub
	}

}
