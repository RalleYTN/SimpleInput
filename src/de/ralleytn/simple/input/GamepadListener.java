package de.ralleytn.simple.input;

/**
 * Listens to gamepad events.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface GamepadListener {

	/**
	 * Called when an analog stick was pushed.
	 * <p><i><b>Warning!</b><br>It can be that if the gamepad is in digital mode that the POV will behave like the left analog stick
	 * and the right analog stick like the button pad.</i></p>
	 * @param event the {@linkplain GamepadEvent}
	 * @since 1.0.0
	 */
	public void onAnalogStickPush(GamepadEvent event);
	
	/**
	 * Called when a button was pressed.
	 * @param event {@linkplain GamepadEvent}
	 * @since 1.0.0
	 */
	public void onButtonPress(GamepadEvent event);
	
	/**
	 * Called when a button was released.
	 * @param event {@linkplain GamepadEvent}
	 * @since 1.0.0
	 */
	public void onButtonRelease(GamepadEvent event);
	
	/**
	 * Called when the POV was pressed.
	 * <p><i><b>Warning!</b><br>It can be that if the gamepad is in digital mode that the POV will behave like the left analog stick
	 * and the right analog stick like the button pad.</i></p>
	 * @param event {@linkplain GamepadEvent}
	 * @since 1.0.0
	 */
	public void onPOVPress(GamepadEvent event);
	
	/**
	 * Called when the POV was released.
	 * <p><i><b>Warning!</b><br>It can be that if the gamepad is in digital mode that the POV will behave like the left analog stick
	 * and the right analog stick like the button pad.</i></p>
	 * @param event {@linkplain GamepadEvent}
	 * @since 1.0.0
	 */
	public void onPOVRelease(GamepadEvent event);
}
