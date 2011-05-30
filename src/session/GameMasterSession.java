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
	private LogoutHandler logoutHandler;
	
	private ArrayList<String> playerIds;
	private ArrayList<String> players;
	private ArrayList<TCPClientFactory> factories;

		
	public GameMasterSession(Display display) {
		this.display = display;
		this.gameMasterGUI = new GameMasterGUI();
		this.serverThread = null;
		this.playerIds = new ArrayList<String>();
		this.factories = new ArrayList<TCPClientFactory>();
		this.players = new ArrayList<String>();
	}

	public void open() {
		shell = gameMasterGUI.open(display);
		initializeGameMasterGUI();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		if(serverThread != null){
			Logout logout = new Logout();
			logout.setName("MG");
			sendAll(logout);
			
			server.finalize();
			try {
				serverThread.join();
			} catch (InterruptedException e) {

			}
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
		
		logoutHandler = new LogoutHandler(new Listener(){
			@Override
			public void handle(Object o, String senderId, final Date dateRecieved, String address){
				final int index = playerIds.lastIndexOf(senderId);
				playerIds.remove(index);
				factories.remove(index);

				display.asyncExec(new Runnable() {
			        public void run(){
			        	gameMasterGUI.newMessage("ARSE", "Użytkownik "+players.get(index)+" opuścił grę.", dateRecieved);
			        }
			    });
				
				Message msg = new Message();
				msg.setAuthor("MG");
				msg.setMessage("Użytkownik "+players.get(index)+" opuścił grę.");
				
				players.remove(index);
				
				sendAll(msg);
			}
		});
		
		playerHandler = new PlayerHandler(new Listener(){
			@Override
			public void handle(Object o, String senderId, final Date dateRecieved, String address){
				final Player newPlayer = (Player) o;
				synchronized(factories){
					if(!playerIds.contains(senderId)){ // login
						display.asyncExec(new Runnable() {
					        public void run(){
					        	gameMasterGUI.newMessage("ARSE", "Użytkownik "+newPlayer.getName()+" dołączył do gry.", dateRecieved);
					        }
					    });
						
						Message msg = new Message();
						msg.setAuthor("MG");
						msg.setMessage("Użytkownik "+newPlayer.getName()+" dołączył do gry.");
						
						sendAll(msg);
						
						players.add(newPlayer.getName());
						playerIds.add(senderId);
						factories.add(new TCPClientFactory(address, port));
					}
				}
			}
		});
		
		messageHandler = new MessageHandler(new Listener(){
			@Override
			public void handle(Object o, String senderId, final Date dateRecieved, String address){
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
		courier.addHandler(logoutHandler);
		
		server = new TCPServer(courier, port);
		serverThread = new Thread(server);
		serverThread.start();
		
		addGUIlisteners();		
	}
	
	private void sendAll(Object o){
		for(int i = 0; i < factories.size(); i++){
			TCPClient client = factories.get(i).tcpClient();
			client.sendObject(o);
		}
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

