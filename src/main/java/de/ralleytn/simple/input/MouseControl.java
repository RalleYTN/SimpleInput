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

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.InputEvent;

import de.ralleytn.simple.input.internal.Util;

/**
 * Responsible for controlling the mouse with a gamepad.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class MouseControl {

	private int lmbMapping;
	private int rmbMapping;
	private int mmbMapping;
	private boolean enabled;
	private int analogStick;
	private float sensity;
	
	MouseControl() {
		
		this.analogStick = GamepadEvent.ANALOG_STICK_LEFT;
		this.sensity = 5.0F;
		this.lmbMapping = GamepadEvent.BUTTON_X;
		this.rmbMapping = GamepadEvent.BUTTON_A;
		this.mmbMapping = GamepadEvent.BUTTON_NONE;
	}
	
	/**
	 * Enables or disables the mouse control.
	 * Default is {@code false}.
	 * @param enabled {@code true} = enabled, {@code false} = disabled
	 * @since 1.0.0
	 */
	public void setEnabled(boolean enabled) {
		
		this.enabled = enabled;
	}
	
	/**
	 * Sets which analog stick will be used to control the mouse cursor.
	 * Default is {@link GamepadEvent#ANALOG_STICK_LEFT}.
	 * @param analogStick the analog stick
	 * @since 1.0.0
	 */
	public void setAnalogStick(int analogStick) {
		
		this.analogStick = analogStick;
	}
	
	/**
	 * Sets the cursor sensity.
	 * The cursor moves faster with higher sensity.
	 * Cursor control can be inverted by setting a negative value.
	 * If the sensity is {@code 0.0F}, the cursor will not move.
	 * Default is {@code 5.0F}.
	 * @param sensity the sensity
	 * @since 1.0.0
	 */
	public void setSensity(float sensity) {
		
		this.sensity = sensity;
	}
	
	/**
	 * Maps the left mouse button to a button on the gamepad.
	 * Default is {@link GamepadEvent#BUTTON_X}.
	 * @param gamepadButton the gamepad button
	 * @since 1.0.0
	 */
	public void mapLeftMouseButtonTo(int gamepadButton) {
		
		this.lmbMapping = gamepadButton;
	}
	
	/**
	 * Maps the right mouse button to a button on the gamepad.
	 * Default is {@link GamepadEvent#BUTTON_A}.
	 * @param gamepadButton the gamepad button
	 * @since 1.0.0
	 */
	public void mapRightMouseButtonTo(int gamepadButton) {
		
		this.rmbMapping = gamepadButton;
	}
	
	/**
	 * Maps the middle mouse button to a button on the gamepad.
	 * Default is {@link GamepadEvent#BUTTON_NONE}.
	 * @param gamepadButton the gamepad button
	 * @since 1.0.0
	 */
	public void mapMiddleMouseButtonTo(int gamepadButton) {
		
		this.mmbMapping = gamepadButton;
	}
	
	protected void processReleaseButtonEvent(int button) {
		
		if(this.enabled) {
			
			       if(button == this.lmbMapping) {Util.ROBOT.mouseRelease(InputEvent.getMaskForButton(java.awt.event.MouseEvent.BUTTON1));
			} else if(button == this.rmbMapping) {Util.ROBOT.mouseRelease(InputEvent.getMaskForButton(java.awt.event.MouseEvent.BUTTON3));
			} else if(button == this.mmbMapping) {Util.ROBOT.mouseRelease(InputEvent.getMaskForButton(java.awt.event.MouseEvent.BUTTON2));
			}
		}
	}
	
	protected void processPressButtonEvent(int button) {
		
		if(this.enabled) {
			
			       if(button == this.lmbMapping) {Util.ROBOT.mousePress(InputEvent.getMaskForButton(java.awt.event.MouseEvent.BUTTON1));
			} else if(button == this.rmbMapping) {Util.ROBOT.mousePress(InputEvent.getMaskForButton(java.awt.event.MouseEvent.BUTTON3));
			} else if(button == this.mmbMapping) {Util.ROBOT.mousePress(InputEvent.getMaskForButton(java.awt.event.MouseEvent.BUTTON2));
			}
		}
	}
	
	protected void updateCursorPosition(float leftX, float leftY, float rightX, float rightY) {
		
		if(this.enabled) {
			
			float x = 0;
			float y = 0;
			
			if(this.analogStick == GamepadEvent.ANALOG_STICK_LEFT) {
				
				x = leftX;
				y = leftY;
				
			} else if(this.analogStick == GamepadEvent.ANALOG_STICK_RIGHT) {
				
				x = rightX;
				y = rightY;
			}
			
			Point cursorPosition = MouseInfo.getPointerInfo().getLocation();
			int xPos = (int)(cursorPosition.x + (x * this.sensity));
			int yPos = (int)(cursorPosition.y + (y * this.sensity));
			Util.ROBOT.mouseMove(xPos, yPos);
		}
	}
	
	/**
	 * @return {@code true} if the mouse control is enabled, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isEnabled() {
		
		return this.enabled;
	}
	
	/**
	 * @return the analog stick with which the mouse is controlled
	 * @since 1.0.0
	 */
	public int getAnalogStick() {
		
		return this.analogStick;
	}
	
	/**
	 * @return the cursor sensity
	 * @since 1.0.0
	 */
	public float getSensity() {
		
		return this.sensity;
	}
	
	/**
	 * @return the gamepad button that is mapped to the left mouse button
	 * @since 1.0.0
	 */
	public int getLeftMouseButtonMapping() {
		
		return this.lmbMapping;
	}
	
	/**
	 * @return the gamepad button that is mapped to the right mouse button
	 * @since 1.0.0
	 */
	public int getRightMouseButtonMapping() {
		
		return this.rmbMapping;
	}
	
	/**
	 * @return the gamepad button that is mapped to the middle mouse button
	 * @since 1.0.0
	 */
	public int getMiddleMouseButtonMapping() {
		
		return this.mmbMapping;
	}
}
