package transmission;

public interface IncommingMessageHandler {
	/**
	 * 
	 * @param classHash - the classHash obtained by StaticHelper.getClassnameHash()
	 * @return tells if your Handler accepts this message type (or rather it's hash)
	 */
	public Boolean acceptsClassHash(String classHash);
	
	/**
	 * Handles the incoming message of the accepted type. It shouldn't block the system - if you're doing something that takes
	 * time, make sure you use threads or sth.
	 * 
	 * @param msg the message your Handler will play with. 
	 * It should already be the type You're accepting using {@link #acceptsClassHash(String)}
	 */
	public void handleIncommingMessage(IncommingMessageContainer msg);
}
