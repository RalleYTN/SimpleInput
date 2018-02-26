package de.ralleytn.simple.input;

/**
 * Implements the {@linkplain GamepadListener} interface.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class GamepadAdapter implements GamepadListener {

	@Override public void onAnalogStickPush(GamepadEvent event) {}
	@Override public void onButtonPress(GamepadEvent event) {}
	@Override public void onButtonRelease(GamepadEvent event) {}
	@Override public void onPOVPress(GamepadEvent event) {}
	@Override public void onPOVRelease(GamepadEvent event) {}
}
