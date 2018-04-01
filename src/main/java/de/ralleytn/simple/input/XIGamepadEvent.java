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
 * Represents an event that was fired by a gamepad that uses the XInput API.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.1.0
 * @since 1.1.0
 */
public class XIGamepadEvent extends GamepadEvent {

	/**
	 * BACK
	 * @since 1.1.0
	 */
	public static final int BUTTON_BACK = BUTTON_SELECT;
	
	/**
	 * LB
	 * @since 1.1.0
	 */
	public static final int BUTTON_LB = BUTTON_L1;
	
	/**
	 * RB
	 * @since 1.1.0
	 */
	public static final int BUTTON_RB = BUTTON_R1;
	
	/**
	 * LT
	 * @since 1.1.0
	 */
	public static final int BUTTON_LT = BUTTON_L2;
	
	/**
	 * RT
	 * @since 1.1.0
	 */
	public static final int BUTTON_RT = BUTTON_R2;
	
	/**
	 * Press the left thumb stick
	 * @since 1.0.0
	 */
	public static final int BUTTON_LTHUMB = BUTTON_L3;
	
	/**
	 * Press the right thumb stick
	 * @since 1.0.0
	 */
	public static final int BUTTON_RTHUMB = BUTTON_R3;
	
	/**
	 * No trigger
	 * @since 1.1.0
	 */
	public static final int TRIGGER_NONE = -1;
	
	/**
	 * LT
	 * @since 1.1.0
	 */
	public static final int TRIGGER_LEFT = 0;
	
	/**
	 * RT
	 * @since 1.1.0
	 */
	public static final int TRIGGER_RIGHT = 1;
	
	private final int trigger;
	
	/**
	 * @param device the device that fired this event
	 * @param direction the direction the POV or an analog stick is facing
	 * @param analogStick the analog stick associated with this event
	 * @param button the button that was either pressed or released
	 * @param intensity the intensity with which an analog stick or trigger is pushed
	 * @param trigger the trigger that is being used
	 * @since 1.1.0
	 */
	public XIGamepadEvent(Device device, Direction direction, int analogStick, int button, float intensity, int trigger) {
		
		super(device, direction, analogStick, button, intensity);
		
		this.trigger = trigger;
	}
	
	/**
	 * Constructor for {@link XIGamepadListener#onButtonPress(GamepadEvent)} and {@link XIGamepadListener#onButtonRelease(GamepadEvent)}
	 * @param device the device that fired this event
	 * @param button the button associated with this event
	 * @param press pressed = {@code true}, released = {@code true}
	 * @since 1.1.0
	 */
	public XIGamepadEvent(Device device, int button, boolean press) {
		
		super(device, button, press);
		
		this.trigger = TRIGGER_NONE;
	}
	
	/**
	 * Constructor for {@link XIGamepadListener#onTriggerPush(GamepadEvent)}.
	 * @param device the device that fired this event
	 * @param trigger the trigger that was pushed
	 * @param intensity the intensity of the trigger push
	 * @since 1.1.0
	 */
	public XIGamepadEvent(Device device, int trigger, float intensity) {
		
		super(device, null, ANALOG_STICK_NONE, BUTTON_NONE, intensity);

		this.trigger = trigger;
	}
	
	/**
	 * @return the trigger that was pushed
	 * @since 1.1.0
	 */
	public final int getTrigger() {
		
		return this.trigger;
	}
}
