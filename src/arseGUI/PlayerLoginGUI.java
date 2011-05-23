package arseGUI;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class PlayerLoginGUI extends GUI{
	private Text nameText;
	private Text addressText;
	private Text portText;
	private Button buttonLogin;

	public Shell open(Display display) {
		final Shell shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX);
		shell.setText("ARSE");
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 30;
		gridLayout.marginWidth = 30;
		shell.setLayout(gridLayout);

		Label nameLabel = new Label(shell, SWT.NONE);
		nameLabel.setText("Nick:");
		
		nameText = new Text(shell, SWT.BORDER);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		nameText.setLayoutData(gridData);

		Label addressLabel = new Label(shell, SWT.NONE);
		addressLabel.setText("Adres serwera:");
		
		addressText = new Text(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.verticalIndent = 10;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		addressText.setLayoutData(gridData);
		
		Label portLabel = new Label(shell, SWT.NONE);
		portLabel.setText("Port:");
		
		portText = new Text(shell, SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		portText.setLayoutData(gridData);
		
		buttonLogin = new Button(shell, SWT.PUSH);
		buttonLogin.setText("Dołącz do sesji");
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = SWT.CENTER;
		gridData.verticalIndent = 10;
		buttonLogin.setLayoutData(gridData);
		shell.setDefaultButton(buttonLogin);
		
		centerWindow(shell, display);
	    
		shell.open();
		return shell;
	}
	
	public String getName(){
		return nameText.getText();
	}
	
	public String getAddress(){
		return addressText.getText();
	}
	
	public String getPort(){
		return portText.getText();
	}

	public void addLoginListener(SelectionListener selectionListener) {
		buttonLogin.addSelectionListener(selectionListener);
	}

	public void alert(String msg, Shell shell) {
		MessageBox dialog = new MessageBox(shell, SWT.ICON_WARNING);
		dialog.setText("ARSE");
		dialog.setMessage(msg);
		dialog.open();
	}
}
