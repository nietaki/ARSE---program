

import java.lang.*;
import java.io.*;
import java.net.*;

public class TestServer implements Runnable {
	UniversalDeserializer ud;
	int port = 1234;
	ServerSocket srvr;
	static String ok = "OK";
	static String err = "ER";
	public TestServer(UniversalDeserializer ud){
		this.ud = ud;
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
			System.out.println(classNameHashBytes);

			System.out.println(dataLength);
			//object representation length has the hard max value at int.MAX_VALUE = 2^31 - 1
			byte[] dataBytes = new byte[dataLength];
			in.read(dataBytes, 0, dataLength);
			
			System.out.println("received msg:");
			System.out.println(dataBytes);
			
			
			//sending response:
			PrintWriter out = new PrintWriter(skt.getOutputStream(), true);

			String data = TestServer.ok;
			System.out.print("Sending string: '" + data + "'\n");
			out.print(data);
			out.close();
			skt.close();
		}catch(IOException e){
			//FIXME
			System.err.println(e.getMessage());
		}

	}
	public static void main(String args[]) {
		System.out.println("Starting the server");
		TestServer ts = new TestServer(new UniversalDeserializer());
		Thread t=new Thread(ts);
		t.run();
//		try {
//			ServerSocket srvr = new ServerSocket(1234);
//			Socket skt = srvr.accept();
//			System.out.print("Server has connected!\n");
//			PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
//			System.out.print("Sending string: '" + data + "'\n");
//			out.print(data);
//			out.close();
//			skt.close();
//			srvr.close();
//		}
//		catch(Exception e) {
//			System.out.println("Whoops! It didn't work!\n");
//			System.out.println(e.getMessage());
//		}
	}
}