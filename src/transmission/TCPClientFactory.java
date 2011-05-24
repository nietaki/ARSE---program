package transmission;

public class TCPClientFactory {
	String address;
	int port;
	public TCPClientFactory(String address, int port){
		this.address = address;
		this.port = port;
	}
	
	public TCPClient tcpClient(){
		return new TCPClient(this.address, this.port);
	}
	
	public static void main(String args[]) {
		TCPClientFactory tcf = new TCPClientFactory("localhost", 1234);
		TCPClient tc = tcf.tcpClient();
		
		MyExampleBean myb = new MyExampleBean();
		myb.setBar("dupa");
		myb.setBaz(3);
		
		tc.sendObject(myb);
		tc.sendObject(new Boolean(false));

	}
}
