package de.ralleytn.simple.input;

import net.java.games.input.Component.Identifier;

/**
 * Represents an event that was triggered by a keyboard.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class KeyboardEvent extends InputEvent {

	private final Identifier id;
	
	/**
	 * @param device the device that fired this event
	 * @param id the identifier of the key that is associated with this event
	 * @since 1.0.0
	 */
	public KeyboardEvent(Device device, Identifier id) {
		
		super(device);
		
		this.id = id;
	}
}
