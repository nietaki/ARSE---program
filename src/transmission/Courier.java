package transmission;

import org.codehaus.jackson.map.*;
public class Courier {
	
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	private static IncommingMessageContainer wrapInContainer(String senderId, 
									String classHashname,  
									byte[] incomingObjectBytes ){
		

		return new IncommingMessageContainer(senderId, classHashname, incomingObjectBytes);


	}
	
	public void processMessage(String senderId, String classHashname, byte[] incomingObjectBytes){
		

		IncommingMessageContainer in  = this.wrapInContainer(senderId, classHashname, incomingObjectBytes);
		//TODO forward it to the handler
		System.out.println("sth received!");
		System.out.println(in.getIncomingObject());

	}
}
