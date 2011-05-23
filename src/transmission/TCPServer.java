package transmission;


import java.lang.*;
import java.io.*;
import java.net.*;

public class TCPServer implements Runnable {
	Courier courier;
	int port = 1234;
	ServerSocket srvr;
	static String ok = "OK";
	static String err = "ER";
	public TCPServer(Courier courier){
		this.courier = courier;
		try{
			this.srvr = new ServerSocket(this.port);
		}catch(IOException e){
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	
	protected void finalize(){
		try{
			this.srvr.close();
		}catch(IOException e){
			//we can ignore that
		}
	}
	
	public void run(){
		while(true){
			try{
				
				Socket skt = srvr.accept();
				System.out.print("Sth has connected to the server!\n");
	
				//receiving request:
				DataInputStream in = new DataInputStream(new BufferedInputStream(skt.getInputStream()));
				
				byte[] appIdBytes = new byte[26];
				byte[] classNameHashBytes = new byte[32];
				int dataLength; 
				//TODO protect against reading less than desired number of bytes
				in.read(appIdBytes, 0, 26);
				in.read(classNameHashBytes, 0, 32);
				dataLength = in.readInt();
				System.out.println(new String(appIdBytes));
				System.out.println(new String(classNameHashBytes));
	
				System.out.println(dataLength);
				//object representation length has the hard max value at int.MAX_VALUE = 2^31 - 1
				byte[] dataBytes = new byte[dataLength];
				in.read(dataBytes, 0, dataLength);
				
				System.out.println("received msg:");
				System.out.println(new String(dataBytes));
				
				courier.processMessage(new String(appIdBytes),new String(classNameHashBytes), dataBytes);
				
				//sending response:
				PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
	
				String data = TCPServer.ok;
				System.out.print("Sending string: '" + data + "'\n");
				out.print(data);
				out.close();
				skt.close();
				
			}catch(IOException e){
				//FIXME
				System.err.println(e.getMessage());
			}
		}

	}
	public static void main(String args[]) {
		System.out.println("Starting the server");
		TCPServer ts = new TCPServer(new Courier());
		Thread t=new Thread(ts);
		t.run();

	}
}