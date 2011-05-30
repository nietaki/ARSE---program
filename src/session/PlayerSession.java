package session;

import java.util.Date;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import arseGUI.PlayerGUI;

import transmission.Courier;
import transmission.MessageHandler;
import transmission.TCPClient;
import transmission.TCPClientFactory;
import transmission.TCPServer;

public class PlayerSession {
	private Display display;
	private Shell shell;
	private PlayerGUI playerGUI;
	
	private Boolean loggedIn;
	
	private Courier courier;
	private MessageHandler messageHandler;
	
	private TCPServer server;
	private Thread serverThread;
	private TCPClientFactory factory; 
	
	private Player player;
	
	protected PlayerSession(Display display) {
		this.display = display;
		this.loggedIn = false;
		this.playerGUI = new PlayerGUI();
		this.player = new Player();
	}

	protected void login(String username, String address, Integer port) throws LoginErrorException{
		if(username.isEmpty()){
			throw new LoginErrorException("Podaj swój nick");
		}
		if(address.isEmpty()){
			throw new LoginErrorException("Podaj adres serwera");
		}
		if(port == null){
			throw new LoginErrorException("Podaj numer portu");
		}
		
		player.setName(username);
		
		courier = new Courier();

		server = new TCPServer(courier, port);
		serverThread = new Thread(server);
		serverThread.start();
		
		factory = new TCPClientFactory(address, port);

		TCPClient client = factory.tcpClient();

		client.sendObject(player);
		/*
		 * 
		 *  TODO TIMEOUT
		 *  
		 */
		synchronized(loggedIn){
			loggedIn = true;	
		}
	}
	
	public void open() {
		shell = playerGUI.open(display);
		initializePlayerGUI();
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

	private void initializePlayerGUI() {
		playerGUI.addNewMessageListener(new SelectionListener(){
			private void sendMsg(){
				String message = playerGUI.sendMessage();
				Message msg = new Message();
				msg.setAuthor(player.getName());
				msg.setMessage(message);
				TCPClient client = factory.tcpClient();
				client.sendObject(msg);
			}
			
			public void widgetSelected(SelectionEvent e) {
				sendMsg();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				sendMsg();
			}
		});
		
		messageHandler = new MessageHandler(new Listener(){
			@Override
			public void handle(Object o, String senderId, final Date dateRecieved) {
				final Message msg = (Message) o;
				display.asyncExec(new Runnable() {
			        public void run(){
			        	playerGUI.newMessage(msg.getAuthor(), msg.getMessage(), dateRecieved);
			        }
			    });
			}
		});
		
		courier.addHandler(messageHandler);
	}

	protected boolean loggedIn() {
		synchronized(loggedIn){
			return loggedIn;			
		}
	}

}
