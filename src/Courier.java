import org.codehaus.jackson.map.*;
public class Courier {
	
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	private static <T> IncommingMessageContainer<T> unpack(Class<T> desiredClass, 
									String senderId,  
									byte[] incomingObjectBytes ){
		
		try{
			T recObject = Courier.objectMapper.readValue(incomingObjectBytes, desiredClass);
			return new IncommingMessageContainer<T>(senderId, recObject);
		}catch(Exception e){
			System.err.println("FIXME");
			System.exit(6);
		
		}
		return null;
	}
	
	public void processMessage(String senderId, String classHashname, byte[] incomingObjectBytes){
		
		//BOOLEAN
		if (classHashname.equals(StaticHelper.getClassnameHash(Boolean.class))){
			IncommingMessageContainer<Boolean> in  = this.unpack(Boolean.class, senderId, incomingObjectBytes);
			//TODO forward it to the handler
			System.out.println("bool received!");
			System.out.println(in.getIncomingObject());
		}else if(classHashname.equals(StaticHelper.getClassnameHash(Courier.class))){
			IncommingMessageContainer<Courier> in  = this.unpack(Courier.class, senderId, incomingObjectBytes);
			  
		}
	}
}
