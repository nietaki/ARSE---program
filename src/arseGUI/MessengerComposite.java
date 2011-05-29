package arseGUI;

import java.text.DateFormat;
import java.util.Date;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

//import session.MessengerLogic;

public class MessengerComposite {

	private final Group group;
	private final ScrolledComposite history_sc;
	private final Composite history_c;
	private final Text newMessage;
	private final Button send;
	private final int WIDTH = 300;
	GridLayout historyGridLayout;

	public MessengerComposite(Shell shell) {
		group = new Group(shell, SWT.SHADOW_IN);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.marginHeight = 10;
		gridLayout.marginWidth = 10;
		gridLayout.verticalSpacing = 20;
		group.setLayout(gridLayout);
		group.setText("Messenger");
		// group.setSize(WIDTH, 400);

		history_sc = new ScrolledComposite(group, SWT.V_SCROLL | SWT.BORDER);
		history_sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));

		history_c = new Composite(history_sc, SWT.NONE);
		history_sc.setContent(history_c);
		history_c.setSize(WIDTH, 200);
		history_sc.setSize(WIDTH, 200);

		historyGridLayout = new GridLayout();
		historyGridLayout.numColumns = 1;
		history_c.setLayout(historyGridLayout);

		newMessage = new Text(group, SWT.MULTI | SWT.BORDER | SWT.WRAP
				| SWT.V_SCROLL);
		GridData newMessageGD = new GridData();
		newMessageGD.widthHint = WIDTH;
		newMessageGD.heightHint = 80;
		newMessage.setLayoutData(newMessageGD);

		send = new Button(group, SWT.PUSH);
		send.setText("Wyślij");
		GridData sendGD = new GridData();
		sendGD.widthHint = WIDTH;
		send.setLayoutData(sendGD);
	}

	public void newMessage(String message, String who, Date date) {
		Label person = new Label(history_c, SWT.NONE);
		person.setText(who + ": " + DateFormat.getTimeInstance().format(date));
		GridData d = new GridData();
		d.grabExcessHorizontalSpace = true;
		person.setLayoutData(d);
		person.setBackground(new Color(null, 100, 150, 200));

		Label content = new Label(history_c, SWT.WRAP);
		GridData d2 = new GridData();
		d2.grabExcessHorizontalSpace = true;
		content.setLayoutData(d2);
		content.setText(message);
		content.setSize(history_sc.computeSize(history_sc.getMinWidth(),
				SWT.DEFAULT));
		history_c.setSize(history_c.computeSize(WIDTH, SWT.DEFAULT));
	}

	public String sendMessage() {
		String res = newMessage.getText();
		newMessage(newMessage.getText(), "Ja", new Date());
		newMessage.setText("");
		return res;
	}

	public void addNewMessageListener(SelectionListener selectionListener) {
		send.addSelectionListener(selectionListener);
	}

}
