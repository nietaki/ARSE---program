package session;

import java.util.ArrayList;

import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import arseGUI.*;
import transmission.*;

public class GameMasterSession {
	private Display display;
	private Shell shell;
	private GameMasterGUI gameMasterGUI;
	
	private Courier courier;
	private TCPServer server;
	private Thread serverThread;
	
	private ArrayList<Player> players;
	private ArrayList<TCPClientFactory> factories;
	
	public GameMasterSession(Display display) {
		this.display = display;
		this.gameMasterGUI = new GameMasterGUI();
		this.serverThread = null;
		this.players = new ArrayList<Player>();
		this.factories = new ArrayList<TCPClientFactory>();
	}

	public void open() {
		shell = gameMasterGUI.open(display);
		initializeGameMasterGUI();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		if(serverThread != null){
			// TODO zabijanie watku
		}
	}

	private void initializeGameMasterGUI() {
		gameMasterGUI.addStartSessionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Integer portNumber = gameMasterGUI.getPortNumber(shell);
				if(portNumber != null){
					courier = new Courier();
					/*
					 * TODO add handlers for classes:
					 * - Message
					 * - Player
					 * to Courier
					 */
					
					server = new TCPServer(courier, portNumber);
					serverThread = new Thread(server);
					serverThread.start();
					
					addGUIlisteners();
				}
			}
		});
	}
	
	private void addGUIlisteners() {
		gameMasterGUI.addNewMessageListener(new SelectionListener(){
			private void sendMsg(){
				String message = gameMasterGUI.sendMessage();
				for(int i = 0; i < factories.size(); i++){
					TCPClient sender = factories.get(i).tcpClient();
					Message msg = new Message();
					msg.setAuthor("MG");
					msg.setMessage(message);
					sender.sendObject(msg);
				}
			}
			
			public void widgetSelected(SelectionEvent e) {
				sendMsg();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				sendMsg();
			}
		});
	}

}
