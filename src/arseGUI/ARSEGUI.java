package arseGUI;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class ARSEGUI extends GUI{
	private Button buttonLogin;
	private Button buttonNewSession;

	public Shell open(Display display) {
		final Shell shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX);
		shell.setText("ARSE");
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.marginTop = 20;
		gridLayout.marginBottom = 30;
		gridLayout.marginWidth = 30;
		shell.setLayout(gridLayout);
		
		Label ARSELabel = new Label(shell, SWT.PUSH);
		ARSELabel.setText("Advanced RPG Session Emulator");
		ARSELabel.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		
		buttonLogin = new Button(shell, SWT.PUSH);
		buttonLogin.setText("Dołącz do sesji");
		GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridData.verticalIndent = 10;
		buttonLogin.setLayoutData(gridData);
		
		buttonNewSession = new Button(shell, SWT.PUSH);
		buttonNewSession.setText("Przygotuj sesję");
		buttonNewSession.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, false));
		
		centerWindow(shell, display);
		shell.open();
		return shell;
	}

	public void addPlayerListener(SelectionListener selectionListener) {
		buttonLogin.addSelectionListener(selectionListener);
	}

	public void addGameMasterListener(SelectionListener selectionListener) {
		buttonNewSession.addSelectionListener(selectionListener);		
	}
}