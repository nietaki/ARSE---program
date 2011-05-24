package transmission;
import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.*;
import java.util.concurrent.locks.ReentrantLock;

public class IncommingMessageContainer {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	private String senderId;
	private String classHashname;
	private Date dateReceived;
	byte[] incomingObjectBytes;
	private Object incomingObject;
	
	private ReentrantLock lock = new ReentrantLock();
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
	
	public <T extends Object /* duh */> T getIncomingObject(Class<T> desiredClass) throws IOException{
		T ret = null;
		try{
			//FIXME
			this.lock.lock();
			if ((this.incomingObject == null) || (this.incomingObject.getClass() != desiredClass)){
				//TODO a fix if someone maps to a wrong class type?
				this.incomingObject = IncommingMessageContainer.objectMapper.readValue(this.incomingObjectBytes, desiredClass);
				ret = (T) this.incomingObject;
			}
			
			
		}catch(IOException e){
			System.err.println("IOException in IncommingMessageContainer.getIncomingObject. Perhaps you are trying to get the wrong type of object from the IncommingMessageContainer? ");
			System.err.println(e.getMessage());
			throw e;
		}finally{
			this.lock.unlock();
		}
		return ret;
	}



}
