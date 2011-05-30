package arseGUI;

import java.util.Date;

import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class PlayerGUI extends GUI{
	
	private MessengerComposite messenger;

	public Shell open(Display display) {
		final Shell shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX);
		shell.setText("ARSE");
	
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 30;
		gridLayout.marginWidth = 30;
		shell.setLayout(gridLayout);
		
		messenger = new MessengerComposite(shell);
		
		centerWindow(shell, display);
	    
		shell.open();
		return shell;
	}

	public void addNewMessageListener(SelectionListener selectionListener) {
		messenger.addNewMessageListener(selectionListener);
	}

	public String sendMessage() {
		return messenger.sendMessage();
	}

	public void newMessage(String author, String message, Date dateRecieved) {
		messenger.newMessage(message, author, dateRecieved);
	}
	
	public void newMessageDisable() {
		messenger.newMessageDisable();
	}
}
