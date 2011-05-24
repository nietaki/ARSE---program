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
}
