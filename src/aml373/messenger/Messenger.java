package aml373.messenger;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * You can either subscribe or publish to a particular topic 
 * (so either push data to a topic or 
 * read the data from the topic).
 * @author andrealin
 */
public class Messenger {
	private Messenger instance; 
	Map<String, Queue<Packet>> topics = new ConcurrentHashMap<>();
	
	
	/**
	 * Singleton class. Private constructor.
	 */
	private Messenger() {}
	
	public Messenger getInstance() {
		if (instance == null) {
			instance = new Messenger();
		}
		return instance;
	}
	
	/**
	 * Publish a packet to a topic.
	 * @param topicId - String identifier of the topic. 
	 * 	These identifiers must be unique.
	 * @param p - packet to publish
	 */
	public void publish(String topicId, Packet p) {
		Queue<Packet> queue;
		queue = topics.containsKey(topicId) ? 
				topics.get(topicId) : 
				new ConcurrentLinkedQueue<>();
		
		queue.offer(p);
		topics.put(topicId, queue);
	}
	
	/**
	 * Subscribe to a topic. You can subscribe to a topic
	 * that hasn't been created yet.
	 * @param topicId
	 */
	public void subscribe(String topicId){
		
	}
}
