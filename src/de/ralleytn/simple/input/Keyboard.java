package de.ralleytn.simple.input;

import java.util.ArrayList;
import java.util.List;

import net.java.games.input.Controller;
import net.java.games.input.Event;

/**
 * Represents a keyboard.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class Keyboard extends Device {

	private List<KeyboardListener> listeners;
	
	/**
	 * @param controller the {@linkplain Controller} that should be wrapped
	 * @since 1.0.0
	 */
	public Keyboard(Controller controller) {
		
		super(controller);
		
		this.listeners = new ArrayList<>();
	}
	
	@Override
	protected void remove() {
		
		this.listeners.forEach(listener -> listener.onRemove());
		this.stopListening();
	}
	
	/**
	 * Adds a {@linkplain KeyboardListener}.
	 * @param listener the {@linkplain KeyboardListener}
	 * @since 1.0.0
	 */
	public void addKeyboardListener(KeyboardListener listener) {
		
		this.listeners.add(listener);
	}
	
	/**
	 * Removes a {@linkplain KeyboardListener}.
	 * @param listener the {@linkplain KeyboardListener}
	 * @since 1.0.0
	 */
	public void removeKeyboardListener(KeyboardListener listener) {
		
		this.listeners.remove(listener);
	}
	
	/**
	 * Removes a {@linkplain KeyboardListener}.
	 * @param index the index of the {@linkplain KeyboardListener}
	 * @since 1.0.0
	 */
	public void removeKeyboardListener(int index) {
		
		this.listeners.remove(index);
	}
	
	/**
	 * @param index the index of the {@linkplain KeyboardListener}
	 * @return the {@linkplain KeyboardListener} with the given index
	 * @since 1.0.0
	 */
	public KeyboardListener getKeyboardListener(int index) {
		
		return this.listeners.get(index);
	}
	
	@Override
	protected void update() {}

	@Override
	protected void onEvent(Event event) {
		
		
	}
}
