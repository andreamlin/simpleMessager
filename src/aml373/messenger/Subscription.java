package aml373.messenger;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A subscription object is bound to a topic.
 * @author andrealin
 *
 */
public class Subscription {
	Queue<Message> messages = new ConcurrentLinkedQueue<>();
	Topic topic;
	MessageHandler handler;
	
	/**
	 * Package private constructor. 
	 * Bind a subscription to a topic.
	 * @param topic to bind to
	 */
	Subscription(Topic topic, MessageHandler handler) {
		this.topic = topic;
		this.handler = handler;
	}
	
	/**
	 * Get one message from the subscription.
	 * @return a message
	 */
	public Message poll() {
		return messages.poll();
	}
	
	/**
	 * Check if there are any messages to be read from the queue.
	 * @return if there are messages in queue
	 */
	public boolean isEmpty() {
		return messages.isEmpty();
	}
	
	/**
	 * Add a message to this subscription. Called by topic.publish()
	 * @param msg to add to subscription
	 */
	void addMessage(Message msg) {
		messages.add(msg);
		handler.handleMessage(msg);
	}
	
	/**
	 * Stop receiving new messages from the topic.
	 */
	public void unsubscribe() {
		topic.unsubscribe(this);
	}
	
	/**
	 * Get messages from this topic of this Subscription.
	 * @return the messages in the topic at 
	 * 	the time this method is called
	 */
	public Queue<Message> getMessages() {
		return topic.getMessages();
	}
}
