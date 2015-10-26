package aml373.messenger;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Topic {
	String identifier;
	Queue<Packet> msgQueue = new ConcurrentLinkedQueue<>();
	
}
