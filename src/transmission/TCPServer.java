package transmission;


import java.lang.*;
import java.io.*;
import java.net.*;

public class TCPServer implements Runnable {
	Courier courier;
	int port;
	ServerSocket srvr;
	static String ok = "OK";
	static String err = "ER";
	
	public TCPServer(Courier courier, int port){
		this.courier = courier;
		this.port = port;
		try{
			this.srvr = new ServerSocket(this.port);
		}catch(IOException e){
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	
	public void finalize(){
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
				System.out.println("Sth has connected to the server!\n");
				//System.out.println("remote address: " + skt.getRemoteSocketAddress().toString());
				String fromAddress = StaticHelper.getAddress(skt.getRemoteSocketAddress());
				System.out.println("real remote address: " + fromAddress);
				
				System.out.println("");
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
				
				courier.processMessage(new String(appIdBytes), new String(classNameHashBytes), dataBytes, fromAddress);
				
				//sending response:
				PrintWriter out = new PrintWriter(skt.getOutputStream(), true);

				String data = TCPServer.ok;
				System.out.print("Sending string: '" + data + "'\n");
				out.print(data);
				out.close();
				skt.close();
				
			}catch(IOException e){
				//FIXME
				//System.err.println(e.getMessage());
				return;
			}
		}

	}
	public static void main(String args[]) {
		System.out.println("Starting the server");
		Courier c = new Courier();
		ExampleBeanHandler selectiveHandler = new ExampleBeanHandler();
		c.addHandler(selectiveHandler);
		TCPServer ts = new TCPServer(c, 1234);
		Thread t=new Thread(ts);
		t.run();

	}
}