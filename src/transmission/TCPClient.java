package transmission;
import java.lang.*;

import java.io.*;
import java.net.*;
import org.codehaus.jackson.map.*;


import org.codehaus.jackson.JsonFactory;
public class TCPClient {
	
	private String address;
	private int port;
	private String msg;
	ObjectMapper objectMapper;
	
	public TCPClient(String address, int port){
		this.address = address;
		this.port = port;
		objectMapper = new ObjectMapper();
	}
	private class SenderRunnable<T> implements Runnable{
		private T objectSent;
		
		public SenderRunnable(T object){
			this.objectSent = object;
		}
		
		public void run(){
			try {
				Socket skt = new Socket(address, port);
				DataOutputStream disout = new DataOutputStream(skt.getOutputStream());
				
				disout.write(StaticHelper.getAppId().getBytes());
				disout.write(StaticHelper.getClassnameHash(objectSent.getClass()).getBytes());
				String msgString;
				byte[] msgBytes = objectMapper.writeValueAsBytes(this.objectSent);
				disout.writeInt(msgBytes.length);
				disout.write(msgBytes);
				disout.flush();

				BufferedReader in = new BufferedReader(new
						InputStreamReader(skt.getInputStream()));


				while (!in.ready()) {}
				System.out.print("Received string: ");
				char[] inmsg = new char[2];
				in.read(inmsg,0, 2); 

				System.out.println(inmsg);
				in.close();
			}catch(Exception e) {
				//FIXME
				System.out.print("Whoops! It didn't work!\n");
				System.out.print(e.getMessage());
			}
		}
	}
	public <T> void sendObject(T object){
		JsonFactory f = new JsonFactory();
		
		SenderRunnable sr = new SenderRunnable(object);
		new Thread(sr).run();
	}
	public static void main(String args[]) {
		TCPClient tc = new TCPClient("localhost", 1234);
		//tc.sendObject(new Boolean(true));
		tc.sendObject(new Boolean(false));

	}
}
