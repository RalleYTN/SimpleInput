package de.ralleytn.simple.input;

import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

/**
 * Represents an input device.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class Device {

	protected Controller controller;
	protected Thread thread;
	protected boolean listening;
	protected long timeBetweenUpdates;
	
	/**
	 * @param controller the {@linkplain Controller} that should be wrapped
	 * @since 1.0.0
	 */
	public Device(Controller controller) {
		
		this.controller = controller;
		this.timeBetweenUpdates = 10;
		this.thread = new Thread(() -> {
			
			try {
				
				while(this.listening) {
					
					this.controller.poll();
					EventQueue queue = this.controller.getEventQueue();
					Event event = new Event();
					
					while(queue.getNextEvent(event)) {
						
						this.update(event);
					}
					
					Thread.sleep(this.timeBetweenUpdates);
				}
				
			} catch(InterruptedException exception) {
				
				exception.printStackTrace();
			}
		});
	}
	
	protected abstract void update(Event event);
	
	/**
	 * Sets how long the thread will wait after an update.
	 * Default value is {@code 10}.
	 * @param millis the time the thread will wait in milliseconds
	 * @since 1.0.0
	 */
	public void setTimeBetweenUpdates(long millis) {
		
		this.timeBetweenUpdates = millis;
	}
	
	/**
	 * Starts the thread that listens to the input events of this device.
	 * @since 1.0.0
	 */
	public void startListening() {
		
		this.listening = true;
		this.thread.start();
	}
	
	/**
	 * Stops the thread that listens to the input events of this device.
	 * @since 1.0,0
	 */
	public void stopListening() {
		
		this.listening = false;
	}
	
	/**
	 * @return the name of this device (not a unique identifier)
	 * @since 1.0.0
	 */
	public String getName() {
		
		return this.controller.getName().trim();
	}
	
	/**
	 * @return {@code true} if this device currently listens to input events, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isListening() {
		
		return this.listening;
	}
	
	/**
	 * @return the time between updates in milliseconds
	 * @since 1.0.0
	 */
	public long getTimeBetweenUpdates() {
		
		return this.timeBetweenUpdates;
	}
}
