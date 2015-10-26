package aml373.messenger;

/**
 * Handler class. Similar to Runnable Interface.
 * To be called when a message from a subscribed topic is received.
 * @author andrealin
 *
 */
public interface MessageHandler{
	
	public void handleMessage(Message msg);
	
}
