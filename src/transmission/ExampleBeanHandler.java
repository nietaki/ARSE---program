package transmission;

import java.io.IOException;

public class ExampleBeanHandler implements IncommingMessageHandler {

	@Override
	public Boolean acceptsClassHash(String classHash) {
		
		//System.out.println(classHash + " == " + StaticHelper.getClassnameHash(MyExampleBean.class));
		//NOTE THE FUCKING .equals() and uncomment the code above if you wish :)
		if(classHash.equals( StaticHelper.getClassnameHash(MyExampleBean.class))){
			System.out.println("hash matches");
			return true;
		}
		return false;
	}

	@Override
	public void handleIncommingMessage(IncommingMessageContainer msg) {
		System.out.println("I received an example bean:");
		try{
			MyExampleBean inObject = msg.getIncomingObject(MyExampleBean.class);
			System.out.println(inObject.toString());
			System.out.println("and it has properties, too");
			System.out.println("inObject.bar = " + inObject.getBar());
			
			
		}catch(IOException e){
			System.err.println("there was a problem with the received object, I can disregard and stop processing or exit the application if it's serious");
			System.err.println(e.getMessage());
			System.exit(2);
		}
		
		
		
	}

}
