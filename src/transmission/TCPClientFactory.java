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
		//tc.sendObject(new Boolean(true));
		tc.sendObject(new Boolean(false));

	}
}
