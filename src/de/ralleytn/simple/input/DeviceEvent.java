package de.ralleytn.simple.input;

/**
 * Represents an abstract input event.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class DeviceEvent {

	protected final Device device;
	
	public DeviceEvent(Device device) {
		
		this.device = device;
	}
	
	/**
	 * @return the input device that fired this event
	 * @since 1.0.0
	 */
	public final Device getDevice() {
		
		return this.device;
	}
}
