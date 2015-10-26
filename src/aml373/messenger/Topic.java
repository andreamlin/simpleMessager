package aml373.messenger;

import java.util.LinkedList;
import java.util.Queue;

public class Topic {
	String identifier;
	Queue<Packet> msgQueue = new LinkedList<>();
	
	public synchronized void addToQueue(Packet p){
		msgQueue.add(p);
	}
}
