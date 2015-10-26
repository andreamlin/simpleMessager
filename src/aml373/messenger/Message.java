package aml373.messenger;

/**
 * Simple Packet class.
 * @author andrealin
 *
 */
public class Message {
	//can't change this body;
	private final String body;
	
	/**
	 * Make new message with specified body.
	 * @param body
	 */
	public Message(String body) {
		if (body == null) {
			throw new IllegalArgumentException("Cannot create "
					+ "a new Message with null body");
		}
		this.body = body; 
	}
	
	public String getBody(){
		return body;
	}

}
