package aml373.messenger;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Topic class, hidden to users. Contains functions to publish,
 * subscribe, and unsubscribe.
 * @author andrealin
 *
 */
public class Topic {
	String identifier; //name for this topic
	Queue<Message> messages = new ConcurrentLinkedQueue<>();
	Set<Subscription> subscriptions = new HashSet<>();
	
	/**
	 * Package-private constructor.
	 */
	Topic(String identifier) {
		this.identifier = identifier;
	}
	
	String getIdentifier() {
		return identifier;
	}
	
	/**
	 * Publish a packet to a topic.
	 * @param topicId - String identifier of the topic. 
	 * 	These identifiers must be unique.
	 * @param p - packet to publish
	 */
	void publish(Message msg) {
		messages.add(msg);
		broadcast(msg);
	}
	
	/**
	 * Subscribe to receive new messages from this topic.
	 * @return
	 */
	Subscription subscribe() {
		Subscription newSub = new Subscription(this);
		subscriptions.add(newSub); 
		return newSub;
	}
	
	private void broadcast(Message msg) {
		for (Subscription subscriber : subscriptions) {
			subscriber.addMessage(msg);
		}
	}
	
	/**
	 * Get messages from this topic.
	 * Public access to this method in Subscription.
	 * @return the messages in the topic at 
	 * 	the time this method is called
	 */
	Queue<Message> getMessages() {
		return new LinkedList<Message>(messages);
	}
	
	/**
	 * Remove a subscription from this topic.
	 * @param subscription
	 */
	void unsubscribe(Subscription subscription) {
		subscriptions.remove(subscription);
	}
	
	@Override
	public String toString() {
		return identifier;
	}
}
