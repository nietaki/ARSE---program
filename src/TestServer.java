

import java.lang.*;
import java.io.*;
import java.net.*;

public class TestServer {
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