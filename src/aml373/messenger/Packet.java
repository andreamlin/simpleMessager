package aml373.messenger;

/**
 * Simple Packet class.
 * @author andrealin
 *
 */
public class Packet {
	private final String body;
	
	public Packet(String body) {
		this.body = body;
	}
	
	public String getBody(){
		return body;
	}
	
	@Override 
	public boolean equals(Object obj){
		if (!(obj instanceof Packet)){
			return false;
		} 
		return body.equals(((Packet)obj).body);
	}
}
