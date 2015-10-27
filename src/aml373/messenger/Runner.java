package aml373.messenger;

import aml373.messenger.Message;

/**
 * Demonstrates how the Messenger API can be used.
 * @author andrealin
 */
public class Runner {
	static final String APSYNDICATE = "APSyndication";
	static final String WSJFEED = "WSJFeed";

	
	//publisher of news
	static Thread associatedPress = new Thread() {
		@Override
		public void run() {
			int i = 0;
			Messenger messenger = Messenger.getInstance();
			while(i < 50) {
				messenger.publish(APSYNDICATE, new Message("AP" + Integer.toString(i++)));
				notifyAll();
				try {
					wait();
				} catch (InterruptedException e) {
//					e.printStackTrace();
				}
			}
		}
	};
	
	//publisher of own material, and republisher of AP 
	static Thread wsj = new Thread() {
		@Override
		public void run() {
			int i = 0;
			Messenger messenger = Messenger.getInstance();
			Subscription ap_sub = messenger.subscribe(APSYNDICATE);
			
			while(i < 50) {
				messenger.publish(WSJFEED, new Message("Wall Street Journal: " + Integer.toString(i++)));
				while (!ap_sub.isEmpty()) {
					messenger.publish(WSJFEED, 
							new Message("Wall Street Journal: " 
									+ ap_sub.poll().getBody()));
				}
				notifyAll();
				try {
					wait();
				} catch (InterruptedException e) {
				}
				i++;
			}
			
		}
	};
	
	//reader of nytimes and wsj
	static Thread banker = new Thread() {
		@Override
		public void run() {
			Messenger messenger = Messenger.getInstance();
			Subscription wsj_sub = messenger.subscribe(WSJFEED);
			Subscription ap_sub = messenger.subscribe(APSYNDICATE);	
			int i = 0;
			while(i < 50){
				while (!ap_sub.isEmpty()) {
					System.out.println("banker reads " + ap_sub.poll().getBody());
				}
				while (!wsj_sub.isEmpty()) {
					System.out.println("banker reads " + wsj_sub.poll().getBody());
				}
				notifyAll();
				try {
					wait(); //sleep for 2 seconds
				} catch (InterruptedException e) {
				}
				i++;
			}	
		}
	};
	
	//reader of AP
	static Thread student =	new Thread() {
		@Override
		public void run() {
			Messenger messenger = Messenger.getInstance();
			Subscription ap_sub = messenger.subscribe(APSYNDICATE);
			while (!ap_sub.isEmpty()) {
				System.out.println("banker reads " + ap_sub.poll().getBody());
			}
			try {
				sleep(1000); //sleep for 2 seconds
			} catch (InterruptedException e) {
			}
		}
	};
	
	public static void main(String[] args) {
		student.start();
		banker.start();
		wsj.start();
		associatedPress.start();
	}
}
