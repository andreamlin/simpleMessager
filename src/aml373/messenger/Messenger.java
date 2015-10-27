package aml373.messenger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * You can either subscribe or publish to a particular topic 
 * (so either push data to a topic or 
 * read the data from the topic). User interacts with this class.
 * @author andrealin
 */
public class Messenger {
	private static Messenger instance; 
	Map<String, Topic> topics = new ConcurrentHashMap<>();
	
	/**
	 * Singleton class. Private constructor.
	 */
	private Messenger() {}
	
	public static Messenger getInstance() {
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
	public void publish(String topicId, Message msg) {
		Topic topic = getTopic(topicId);
		topic.publish(msg);
	}
	
	public Topic getTopic(String topicId) {
		Topic topic;
		if (!topics.containsKey(topicId)) {
			topic = new Topic(topicId);
			topics.put(topicId, topic);
		} else {
			topic = topics.get(topicId);
		}
		return topic;
	}
	
	/**
	 * Subscribe to a topic. You can subscribe to a topic
	 * that hasn't been created yet.
	 * @param topicId
	 */
	public Subscription subscribe(String topicId){
		Topic topic = getTopic(topicId);
		return topic.subscribe();
	}
}
