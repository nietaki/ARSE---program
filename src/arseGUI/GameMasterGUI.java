package arseGUI;

import java.util.Date;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class GameMasterGUI extends GUI {
	private MenuItem startSessionItem;
	private MenuItem imageEditorItem;
	private MenuItem musicEditorItem;
	private MenuItem notesItem;
	private MenuItem saveSessionItem;
	private MessengerComposite messenger;
	private Integer portNumber = null;
	
	public Shell open(Display display) {
		final Shell shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX);
		shell.setText("ARSE");
	
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 30;
		gridLayout.marginWidth = 30;
		shell.setLayout(gridLayout);
		
		createMenuBar(shell);

		messenger = new MessengerComposite(shell);
		
		centerWindow(shell, display);
	    
		shell.open();
		return shell;
	}

	private Menu createMenuBar(Shell shell) {
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		
		createFileMenu(menuBar, shell);
		
		return menuBar;
	}

	private void createFileMenu(Menu menuBar, final Shell shell) {
		MenuItem item = new MenuItem(menuBar, SWT.CASCADE);
		item.setText("Plik");
		Menu menu = new Menu(shell, SWT.DROP_DOWN);
		item.setMenu(menu);
		
		//File -> Start Session
		startSessionItem = new MenuItem(menu, SWT.NONE);
		startSessionItem.setText("Rozpocznij sesję");
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		//File -> Image Editor
		imageEditorItem = new MenuItem(menu, SWT.NONE);
		imageEditorItem.setText("Organizator obrazków");

		//File -> Music Editor
		musicEditorItem = new MenuItem(menu, SWT.NONE);
		musicEditorItem.setText("Organizator muzyki");

		//File -> Notes Editor.
		notesItem = new MenuItem(menu, SWT.NONE);
		notesItem.setText("Notatnik");
		
		//File -> Save Session.
		saveSessionItem = new MenuItem(menu, SWT.NONE);
		saveSessionItem.setText("Zapisz sesję");
			
		new MenuItem(menu, SWT.SEPARATOR);
		
		//File -> Exit.
		MenuItem subItem = new MenuItem(menu, SWT.NONE);
		subItem.setText("Zakończ");
		subItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
	}

	public void addStartSessionListener(SelectionAdapter selectionAdapter) {
		startSessionItem.addSelectionListener(selectionAdapter);
	}
	public void addStartImageEditorListener(SelectionAdapter selectionAdapter) {
		imageEditorItem.addSelectionListener(selectionAdapter);
	}
	public void addStartMusicEditorListener(SelectionAdapter selectionAdapter) {
		musicEditorItem.addSelectionListener(selectionAdapter);
	}
	public void addStartNotesEditorListener(SelectionAdapter selectionAdapter) {
		notesItem.addSelectionListener(selectionAdapter);
	}
	public void addSaveSessionListener(SelectionAdapter selectionAdapter) {
		saveSessionItem.addSelectionListener(selectionAdapter);
	}

	public Integer getPortNumber(Shell parent) {
		final Shell shell = new Shell(parent, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setText("Rozpocznij sesję");

		shell.setLayout(new GridLayout(2, true));

		Label label = new Label(shell, SWT.NULL);
		label.setText("Podaj numer portu:");

		final Text text = new Text(shell, SWT.SINGLE | SWT.BORDER);

		final Button buttonOK = new Button(shell, SWT.PUSH);
		buttonOK.setText("Rozpocznij sesję");
		buttonOK.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		Button buttonCancel = new Button(shell, SWT.PUSH);
		buttonCancel.setText("Anuluj");

		text.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				try {
					portNumber = new Integer(text.getText());
					buttonOK.setEnabled(true);
		        } catch (Exception e) {
		        	buttonOK.setEnabled(false);
		        }
			}
		});

		buttonOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
		        shell.dispose();
		    }
		});

		buttonCancel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				portNumber = null;
		        shell.dispose();
		    }
		});
		    
		shell.addListener(SWT.Traverse, new Listener() {
			public void handleEvent(Event event) {
				if(event.detail == SWT.TRAVERSE_ESCAPE)
					event.doit = false;
		    }
		});

		text.setText("");
		shell.pack();
		shell.open();

		Display display = parent.getDisplay();
		centerWindow(shell, display);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
		        display.sleep();
		}

	    return portNumber;
	}
	
	public void addNewMessageListener(SelectionListener selectionListener){
		messenger.addNewMessageListener(selectionListener);
	}

	public String sendMessage() {
		return messenger.sendMessage();
	}

	public void newMessage(String author, String message, Date dateRecieved) {
		messenger.newMessage(message, author, dateRecieved);
	}
}
