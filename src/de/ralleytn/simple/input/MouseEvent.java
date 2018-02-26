package de.ralleytn.simple.input;

import java.awt.MouseInfo;
import java.awt.Point;

import net.java.games.input.Component.Identifier;

/**
 * Represents an event that can be fired by mice.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class MouseEvent extends DeviceEvent {

	/**
	 * Left mouse button
	 * @since 1.0.0
	 */
	public static final int BUTTON_LEFT = 0;
	
	/**
	 * Right mouse button
	 * @since 1.0.0
	 */
	public static final int BUTTON_RIGHT = 2;
	
	/**
	 * Middle mouse button
	 * @since 1.0.0
	 */
	public static final int BUTTON_MIDDLE = 1;
	
	private final float deltaX;
	private final float deltaY;
	private final float deltaZ;
	private final Identifier id;
	private final int x;
	private final int y;
	
	public MouseEvent(Device device, float deltaX, float deltaY, float deltaZ, Identifier id) {
		
		super(device);
		
		Point location = MouseInfo.getPointerInfo().getLocation();
		
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.deltaZ = deltaZ;
		this.id = id;
		this.x = location.x;
		this.y = location.y;
	}
	
	/**
	 * @return the cursor position on the X axis on the screen
	 * @since 1.0.0
	 */
	public final int getX() {
		
		return this.x;
	}
	
	/**
	 * @return the cursor position on the Y axis on the screen
	 * @since 1.0.0
	 */
	public final int getY() {
		
		return this.y;
	}
	
	/**
	 * @return the amount of pixel the cursor moved on the X axis since the last update
	 * @since 1.0.0
	 */
	public final int getDeltaX() {
		
		return (int)this.deltaX;
	}
	
	/**
	 * @return the amount of pixel the cursor moved on the Y axis since the last update
	 * @since 1.0.0
	 */
	public final int getDeltaY() {
		
		return (int)this.deltaY;
	}
	
	/**
	 * @return {@code -1} if the mouse wheel was scrolled down, {@code 1} if it was scrolled up
	 * @since 1.0.0
	 */
	public final float getDeltaZ() {
		
		return this.deltaZ;
	}
	
	/**
	 * @return the mouse button associated with this event
	 * @since 1.0.0
	 */
	public final int getButton() {
		
		return this.id == Identifier.Button.LEFT ? MouseEvent.BUTTON_LEFT :
			   this.id == Identifier.Button.MIDDLE ? MouseEvent.BUTTON_MIDDLE :
			   this.id == Identifier.Button.RIGHT ? MouseEvent.BUTTON_RIGHT :
		       -1;
	}
}
