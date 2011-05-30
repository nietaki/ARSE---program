package session;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import arseGUI.*;
import transmission.*;

public class GameMasterSession {
	private Display display;
	private Shell shell;
	private GameMasterGUI gameMasterGUI;
	
	private int port;
	private Courier courier;
	private TCPServer server;
	private Thread serverThread;
	private MessageHandler messageHandler;
	private PlayerHandler playerHandler;
	
	private ArrayList<String> playerIds;
	private ArrayList<TCPClientFactory> factories;
		
	public GameMasterSession(Display display) {
		this.display = display;
		this.gameMasterGUI = new GameMasterGUI();
		this.serverThread = null;
		this.playerIds = new ArrayList<String>();
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
			/*
			 * 
			 *  TODO zabijanie watku
			 *  
			 */
		}
	}

	private void initializeGameMasterGUI() {
		gameMasterGUI.addStartSessionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Integer portNumber = gameMasterGUI.getPortNumber(shell);
				if(portNumber != null){
					port = portNumber;
					startSession();
				}
			}
		});
	}
	
	protected void startSession(){
		courier = new Courier();
		
		playerHandler = new PlayerHandler(new Listener(){
			@Override
			public void handle(Object o, String senderId, Date dateRecieved){
				synchronized(factories){
					if(!playerIds.contains(senderId)){
						Player newPlayer = (Player) o;
						playerIds.add(senderId);
						/*
						 * 
						 * TODO SKAD MAM WZIAC port i adres???????????????????
						 * 
						 */
						factories.add(new TCPClientFactory("192.168.0.14", port));
					}	
				}
			}
		});
		
		messageHandler = new MessageHandler(new Listener(){
			@Override
			public void handle(Object o, String senderId, final Date dateRecieved){
				synchronized(factories){
					if(playerIds.contains(senderId)){
						final Message msg = (Message) o;
						display.asyncExec(new Runnable() {
					        public void run(){
					        	gameMasterGUI.newMessage(msg.getAuthor(), msg.getMessage(), dateRecieved);
					        }
					    });
						
						for(int i = 0; i < factories.size(); i++){
							if(playerIds.get(i).compareTo(senderId) != 0){
								TCPClient client = factories.get(i).tcpClient();
								client.sendObject(msg);
							}
						}
					}	
				}
			}
		});
		
		courier.addHandler(playerHandler);
		courier.addHandler(messageHandler);
		
		server = new TCPServer(courier, port);
		serverThread = new Thread(server);
		serverThread.start();
		
		addGUIlisteners();		
	}
	
	private void addGUIlisteners() {
		gameMasterGUI.addNewMessageListener(new SelectionListener(){
			private void sendMsg(){
				String message = gameMasterGUI.sendMessage();
				synchronized(factories){
					for(int i = 0; i < factories.size(); i++){
						TCPClient sender = factories.get(i).tcpClient();
						Message msg = new Message();
						msg.setAuthor("MG");
						msg.setMessage(message);
						sender.sendObject(msg);
					}	
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

