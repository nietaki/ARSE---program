package transmission;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.*;

public class Courier {

	LinkedList<IncommingMessageHandler> handlers;
	
	public Courier(){
		this.handlers = new LinkedList<IncommingMessageHandler>();
	}
	
	public void processMessage(String senderId, String classHashname, byte[] incomingObjectBytes, String fromAddress){
		
		

		IncommingMessageContainer in  = new IncommingMessageContainer(senderId, classHashname, incomingObjectBytes, fromAddress);
		//TODO forward it to the handler
		System.out.println("sth received!");
		this.notifyHandlers(in);
		
		//System.out.println(in.getIncomingObject(Integer.class));

	}
	
	public void addHandler(IncommingMessageHandler newHandler){
		this.handlers.add(newHandler);
	}
	
	public void removeHandler(IncommingMessageHandler unneededHandler){
		this.handlers.remove(unneededHandler);
	}
	
	private void notifyHandlers(IncommingMessageContainer in){
		if(! this.handlers.isEmpty()){
			Iterator<IncommingMessageHandler> it = handlers.iterator();
			while(it.hasNext()){
				IncommingMessageHandler current = it.next();
				if(current.acceptsClassHash(in.getClassHashname())){
					current.handleIncommingMessage(in);
				}
			}
		}
	}
	public static void main(String args[]) throws Exception {
		

		
		
//		System.exit(0);
//		LinkedList<Integer> l = new LinkedList<Integer>();
//		Object o = l;
//		
//		
//		System.out.println(o.getClass().toString());
	}
}
