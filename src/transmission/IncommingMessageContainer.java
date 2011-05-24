package transmission;
import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.*;

public class IncommingMessageContainer {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	private String senderId;
	private String classHashname;
	private Date dateReceived;
	byte[] incomingObjectBytes;
	private Object incomingObject;
	public IncommingMessageContainer(String senderId, String classHashname, byte[] incomingObjectBytes){
		this.senderId = senderId;
		this.classHashname = classHashname ;
		this.incomingObjectBytes = incomingObjectBytes;
		
		this.dateReceived = new Date();
	}
	

	public String getSenderId() {
		return senderId;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public String getClassHashname() {
		return classHashname;
	}
	
	public <T extends Object /* duh */> T getIncomingObject(Class<T> desiredClass) {
		try{
			if (this.incomingObject == null){
				this.incomingObject = IncommingMessageContainer.objectMapper.readValue(this.incomingObjectBytes, desiredClass);
			}
			return (T) incomingObject;
			
		}catch(IOException e){
			System.err.println("IOException in IncommingMessageContainer.getIncomingObject. This shouldn't happen");
			System.exit(2);
		}
		catch(ClassCastException e){
			throw e;
		
		}
		return null;
	}



}
