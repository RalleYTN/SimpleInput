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

/**
 * Represents an event that was fired by a gamepad.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class GamepadEvent extends DeviceEvent {

	/**
	 * The left analog stick
	 * @since 1.0.0
	 */
	public static final int ANALOG_STICK_LEFT = 0;
	
	/**
	 * The right analog stick
	 * @since 1.0.0
	 */
	public static final int ANALOG_STICK_RIGHT = 1;
	
	/**
	 * A
	 * @since 1.0.0
	 */
	public static final int BUTTON_A = 0;
	
	/**
	 * B
	 * @since 1.0.0
	 */
	public static final int BUTTON_B = 1;
	
	/**
	 * X
	 * @since 1.0.0
	 */
	public static final int BUTTON_X = 2;
	
	/**
	 * Y
	 * @since 1.0.0
	 */
	public static final int BUTTON_Y = 3;
	
	/**
	 * Mode (if {@link Gamepad#getButtonCount()} returns {@code 12} instead of {@code 13}, this button is not supported by the gamepad)
	 * @since 1.0.0
	 */
	public static final int BUTTON_MODE = 12;
	
	/**
	 * Start
	 * @since 1.0.0
	 */
	public static final int BUTTON_START = 9;
	
	/**
	 * Select
	 * @since 1.0.0
	 */
	public static final int BUTTON_SELECT = 8;
	
	/**
	 * R1
	 * @since 1.0.0
	 */
	public static final int BUTTON_R1 = 5;
	
	/**
	 * R2
	 * @since 1.0.0
	 */
	public static final int BUTTON_R2 = 7;
	
	/**
	 * R3 (press right analog stick)
	 * @since 1.0.0
	 */
	public static final int BUTTON_R3 = 11;
	
	/**
	 * L1
	 * @since 1.0.0
	 */
	public static final int BUTTON_L1 = 4;
	
	/**
	 * L2
	 * @since 1.0.0
	 */
	public static final int BUTTON_L2 = 6;
	
	/**
	 * L3 (press left analog stick)
	 * @since 1.0.0
	 */
	public static final int BUTTON_L3 = 10;
	
	private final Direction direction;
	private final int analogStick;
	private final int button;
	private final float intensity;
	
	/**
	 * @param device the device that fired this event
	 * @param direction the direction the POV or an analog stick is facing
	 * @param analogStick the analog stick associated with this event
	 * @param button the button that was either pressed or released
	 * @param intensity the intensity with which an analog stick is pushed
	 * @since 1.0.0
	 */
	public GamepadEvent(Device device, Direction direction, int analogStick, int button, float intensity) {
		
		super(device);
		
		this.direction = direction;
		this.analogStick = analogStick;
		this.button = button;
		this.intensity = intensity;
	}
	
	/**
	 * @return the direction that either the POV or an analog stick is facing
	 * @since 1.0.0
	 */
	public final Direction getDirection() {
		
		return this.direction;
	}
	
	/**
	 * @return the used analog stick
	 * @since 1.0.0
	 */
	public final int getAnalogStick() {
		
		return this.analogStick;
	}
	
	/**
	 * @return the button associated with this event
	 * @since 1.0.0
	 */
	public final int getButton() {
		
		return this.button;
	}
	
	/**
	 * @return the intensity with which an analog stick is pushed
	 * @since 1.0.0
	 */
	public final float getIntensity() {
		
		return this.intensity;
	}
}
