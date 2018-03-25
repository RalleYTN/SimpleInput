/*
 * MIT License
 * 
 * Copyright (c) 2018 Ralph Niemitz
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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

	Device(Controller controller) {
		
		this.controller = controller;
		this.timeBetweenUpdates = 10;
		this.thread = new Thread(() -> {
			
			try {
				
				while(this.listening) {
					
					boolean stillConnected = this.controller.poll();
					
					if(stillConnected) {
						
						EventQueue queue = this.controller.getEventQueue();
						Event event = new Event();
						
						while(queue.getNextEvent(event)) {
							
							this.onEvent(event);
						}
						
						this.update();
						Thread.sleep(this.timeBetweenUpdates);
					
					} else {
						
						this.remove();
						DeviceManager.removeDevice(this);
					}
				}
				
			} catch(InterruptedException exception) {
				
				exception.printStackTrace();
			}
		});
	}
	
	/**
	 * Called whenever JInput can detect an event from this device.
	 * @param event the JInput event
	 * @since 1.0.0
	 */
	protected abstract void onEvent(Event event);
	
	/**
	 * Called when the device was removed from its port.
	 * @since 1.0.0
	 */
	protected abstract void remove();
	
	/**
	 * Called continuously in the listening thread.
	 * The method body is empty.
	 * Overriding this method will give you more control over the device thread.
	 * @since 1.0.0
	 */
	protected void update() {
		
		// IMPLEMENTATION IS DONE BY SUB CLASSES
	}
	
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
	
	/**
	 * @return the port number of this device (currently always {@code 0})
	 * @since 1.0.0
	 */
	public int getPortNumber() {
		
		return this.controller.getPortNumber();
	}

	protected Controller getController() {
		
		return this.controller;
	}
}
