package session;

import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import arseGUI.*;
import transmission.*;

public class GameMasterSession {
	private Display display;
	private Shell shell;
	private GameMasterGUI gameMasterGUI;
	private TCPServer server;
	
	public GameMasterSession(Display display) {
		this.display = display;
		this.gameMasterGUI = new GameMasterGUI();
	}

	public void open() {
		shell = gameMasterGUI.open(display);
		initializeGameMasterGUI();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
	}

	private void initializeGameMasterGUI() {
		gameMasterGUI.addStartSessionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Integer portNumber = gameMasterGUI.getPortNumber(shell);
				if(portNumber != null){
					//server = new TCPServer(portNumber);
					/*
					 * TODO ( server.run(); ? )
					 */
				}
			}
		});
	}

}
