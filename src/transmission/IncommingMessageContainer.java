package transmission;
import java.util.Date;
public class IncommingMessageContainer {
	private String senderId;
	private String classHashname;
	private Date dateReceived;
	byte[] incomingObjectBytes;
	private Object incomingObject;
	public IncommingMessageContainer(String senderId, String classHashname, byte[] incomingObjectBytes){
		this.senderId = senderId;
		this.classHashname = classHashname;
		
		//TODO - instantate with the first object request
		//this.incomingObject = incomingObject;
		this.dateReceived = new Date();
	}
	
	private void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}
	public Date getDateReceived() {
		return dateReceived;
	}
	public void setIncomingObject(Object incomingObject) {
		this.incomingObject = incomingObject;
	}
	public Object getIncomingObject() {
		return incomingObject;
	}
}
