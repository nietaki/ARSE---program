package session;

import java.util.Date;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import arseGUI.PlayerGUI;

import transmission.Courier;
import transmission.LogoutHandler;
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
	private LogoutHandler logoutHandler;
	
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
		loggedIn = true;	
	}
	
	public void open() {
		shell = playerGUI.open(display);
		initializePlayerGUI();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		logout();
	}

	private void logout() {
		if(factory != null){
			Logout logout = new Logout();
			logout.setName(player.getName());
			TCPClient client = factory.tcpClient();
			client.sendObject(logout);	
		}
		
		loggedIn = false;
		
		server.finalize();
		try {
			serverThread.join();
		} catch (InterruptedException e) {

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
			public void handle(Object o, String senderId, final Date dateRecieved, String address) {
				final Message msg = (Message) o;
				display.asyncExec(new Runnable() {
			        public void run(){
			        	playerGUI.newMessage(msg.getAuthor(), msg.getMessage(), dateRecieved);
			        }
			    });
			}
		});
		
		logoutHandler = new LogoutHandler(new Listener(){
			@Override
			public void handle(Object o, String senderId, final Date dateRecieved, String address) {
				display.asyncExec(new Runnable() {
			        public void run(){
			        	playerGUI.newMessage("MG", "Koniec sesji", dateRecieved);
			        }
			    });
				
				factory = null;
				courier.removeHandler(messageHandler);
				courier.removeHandler(logoutHandler);
				playerGUI.addNewMessageListener(new SelectionListener(){
					public void widgetSelected(SelectionEvent e) {}
					public void widgetDefaultSelected(SelectionEvent e) {}
				});
			}
		});
		
		courier.addHandler(messageHandler);
		courier.addHandler(logoutHandler);
	}

	protected boolean loggedIn() {
		return loggedIn;			
	}

}
//192.168.0.14