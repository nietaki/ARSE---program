package arseGUI;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class GameMasterGUI extends GUI {
	
	public Shell open(Display display) {
		final Shell shell = new Shell(display);
		shell.setText("ARSE");

		//TODO
		
		centerWindow(shell, display);
	    
		shell.open();
		return shell;
	}
}