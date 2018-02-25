package de.ralleytn.simple.input;

import net.java.games.input.Controller;
import net.java.games.input.Event;

/**
 * Represents a keyboard.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class Keyboard extends Device {

	/**
	 * @param controller the {@linkplain Controller} that should be wrapped
	 * @since 1.0.0
	 */
	public Keyboard(Controller controller) {
		
		super(controller);
	}

	@Override
	protected void update(Event event) {
		
		
	}
}
