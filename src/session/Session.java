package session;

import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

import arseGUI.*;

public class Session {
	private Display display;
	private Shell shell;
	private ARSEGUI arseGUI;
	private PlayerLoginGUI playerLoginGUI;
	private PlayerSession playerSession;
	private GameMasterSession gameMasterSession;
	private boolean gameMasterMode;
	private boolean playerMode;
	
	protected Session(Display display) {
		this.display = display;
		arseGUI = new ARSEGUI();
		gameMasterMode = false;
		playerMode = false;
	}

	public static void main(String[] args) {
		Display display = new Display();
		Session session = new Session(display);
		session.runSession();
		display.dispose();
	}

	private void runSession() {
		while(true){
			shell = arseGUI.open(display);
			initializeARSE();
			while(!shell.isDisposed()){
				if(!display.readAndDispatch())
					display.sleep();
			}
			
			if(gameMasterMode){
				gameMasterSession = new GameMasterSession(display);
				gameMasterSession.open();
				
				gameMasterMode = false;
			}
			else if(playerMode){
				playerLoginGUI = new PlayerLoginGUI();
				playerSession = new PlayerSession(display);
				shell = playerLoginGUI.open(display);
				initializePlayerLoginGUI();
				while(!shell.isDisposed()){
					if(!display.readAndDispatch())
						display.sleep();
				}
				
				if(playerSession.loggedIn()){
					playerSession.open();
				}
				playerMode = false;
			}
			else break;
		}		
	}

	
	private void initializePlayerLoginGUI() {
		playerLoginGUI.addLoginListener(new SelectionListener(){
			private void login(){
				try{
					playerSession.login(playerLoginGUI.getName(), playerLoginGUI.getAddress(), playerLoginGUI.getPort());
					shell.dispose();
				} catch(LoginErrorException e){
					playerLoginGUI.alert(e.getMessage(), shell);
				}
			}
			
			public void widgetSelected(SelectionEvent e) {
				login();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				login();
			}
		});
	}

	private void initializeARSE() {
		arseGUI.addPlayerListener(new SelectionListener(){
			public void widgetSelected(SelectionEvent e) {
				playerMode = true;
				shell.dispose();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				playerMode = true;
				shell.dispose();
			}
		});
		arseGUI.addGameMasterListener(new SelectionListener(){
			public void widgetSelected(SelectionEvent e) {
				gameMasterMode = true;
				shell.dispose();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				gameMasterMode = true;
				shell.dispose();
			}
		});
	}
}
