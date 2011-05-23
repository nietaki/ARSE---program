import java.util.Date;
public class IncommingMessageContainer<T> {
	private String senderId;
	private Date dateReceived;
	private T incomingObject;
	public IncommingMessageContainer(String senderId, T incomingObject){
		this.senderId = senderId;
		this.incomingObject = incomingObject;
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
	public void setIncomingObject(T incomingObject) {
		this.incomingObject = incomingObject;
	}
	public T getIncomingObject() {
		return incomingObject;
	}
}
