package de.ralleytn.simple.input;

/**
 * Listens to keyboard events.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface KeyboardListener {

	/**
	 * Called when a key was pressed.
	 * @param event the {@linkplain KeyboardEvent}
	 * @since 1.0.0
	 */
	public void onKeyPress(KeyboardEvent event);
	
	/**
	 * Called when a key was released.
	 * @param event the {@linkplain KeyboardEvent}
	 * @since 1.0.0
	 */
	public void onKeyRelease(KeyboardEvent event);
}
