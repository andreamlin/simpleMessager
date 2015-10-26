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
				try {
					sleep(2000); //sleep for 2 seconds
				} catch (InterruptedException e) {
					e.printStackTrace();
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
			MessageHandler readAndPublish = new MessageHandler() {
				@Override
				public void handleMessage(Message msg){
					Messenger.getInstance().publish(WSJFEED, 
							new Message("Wall Street Journal: " 
									+ msg.getBody()));
				}
			};
			
			messenger.subscribe(APSYNDICATE, readAndPublish);
			
			while(i < 50) {
				messenger.publish(WSJFEED, new Message("Wall Street Journal: " + Integer.toString(i++)));
				try {
					sleep(1000); //sleep for 2 seconds
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	};
	
	//reader of nytimes and wsj
	static Thread banker = new Thread() {
		@Override
		public void run() {
			Messenger messenger = Messenger.getInstance();
			MessageHandler readHandler = new MessageHandler() {
				@Override
				public void handleMessage(Message msg){
					System.out.println("banker finds \"" + msg.getBody() + "\" to be interesting");
				}
			};
			messenger.subscribe(WSJFEED, readHandler);
			messenger.subscribe(APSYNDICATE, readHandler);	
			while(true) {
				int i = 0;
			}
		}
	};
	
	//reader of AP
	static Thread student =	new Thread() {
		@Override
		public void run() {
			Messenger messenger = Messenger.getInstance();
			MessageHandler readHandler = new MessageHandler() {
				public void handleMessage(Message msg){
					System.out.println("student reads article \"" 
							+ msg.getBody() + "\"");
				}
			};
			messenger.subscribe(APSYNDICATE, readHandler);
		}
	};
	
	public static void main(String[] args) {
		student.start();
		banker.start();
		wsj.start();
		associatedPress.start();
	}
}
