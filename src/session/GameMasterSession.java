package session;

import org.eclipse.swt.widgets.*;
import arseGUI.*;

public class GameMasterSession {
	private Display display;
	private Shell shell;
//	private GameMasterGUI gameMasterGUI;
	
	public GameMasterSession(Display display) {
		this.display = display;
	}

	public void open() {
/*		gameMasterGUI = new GameMasterGUI();
		shell = gameMasterGUI.open(display);
		initializeGameMasterGUI();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		*/
	}

	private void initializeGameMasterGUI() {
		// TODO Auto-generated method stub
		
	}

}
