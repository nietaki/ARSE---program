import java.lang.*;
import java.io.*;
import java.net.*;
public class TestClient {

	public static void main(String args[]) {
		try {
			Socket skt = new Socket("localhost", 1234);
			
			DataOutputStream disout = new DataOutputStream(skt.getOutputStream());
			String appId = StaticHelper.getAppId();
			String classnameHash = StaticHelper.getClassnameHash(Boolean.class);
			int len = 3;
			String msg = "lol";
			disout.writeBytes(appId);
			System.out.println(appId);
			disout.writeBytes(classnameHash);
			System.out.println(classnameHash);
			disout.writeInt(len);
			//TODO fix if we're sending some kind of multibyte bullshit
			disout.writeChars(msg);
			disout.flush();
			BufferedReader in = new BufferedReader(new
					InputStreamReader(skt.getInputStream()));
			

			while (!in.ready()) {}
			System.out.print("Received string: '");
			char[] inmsg = new char[2];
			in.read(inmsg,0, 2); // Read one line and output it

			System.out.println(inmsg);
			in.close();
		}
		catch(Exception e) {
			System.out.print("Whoops! It didn't work!\n");
			System.out.print(e.getMessage());
		}
	}
}
