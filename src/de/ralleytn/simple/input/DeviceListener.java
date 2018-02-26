package de.ralleytn.simple.input;

/**
 * Contains the methods that all device listener need.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface DeviceListener {

	/**
	 * Called when the device was removed from its port.
	 * Not guaranteed to work!
	 * @since 1.0.0
	 */
	public default void onRemove() {}
}
