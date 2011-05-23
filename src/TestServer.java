

import java.lang.*;
import java.io.*;
import java.net.*;

public class TestServer implements Runnable {
	UniversalDeserializer ud;
	int port;
	ServerSocket srvr;
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
			String data = "dupa";
			System.out.print("Sth has connected to the server!\n");

			//receiving request:
			DataInputStream in = new DataInputStream(skt.getInputStream());
			
			byte[] appIdBytes = new byte[26];
			byte[] classNameHashBytes = new byte[32];
			long dataLength; 
			//TODO protect against reading less than desired number of bytes
			in.read(appIdBytes, 0, 26);
			in.read(classNameHashBytes, 0, 32);
			dataLength = in.readLong();
			


			//sending response:
			PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
			System.out.print("Sending string: '" + data + "'\n");
			out.print(data);
			out.close();
			skt.close();
		}catch(IOException e){
			//FIXME
		}

	}
	public static void main(String args[]) {
		String data = "Toobie ornaught toobie";
		System.out.println(data);
		try {
			ServerSocket srvr = new ServerSocket(1234);
			Socket skt = srvr.accept();
			System.out.print("Server has connected!\n");
			PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
			System.out.print("Sending string: '" + data + "'\n");
			out.print(data);
			out.close();
			skt.close();
			srvr.close();
		}
		catch(Exception e) {
			System.out.println("Whoops! It didn't work!\n");
			System.out.println(e.getMessage());
		}
	}
}