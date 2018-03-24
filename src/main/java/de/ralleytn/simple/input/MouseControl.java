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
		this.mmbMapping = -1;
	}
	
	public void setEnabled(boolean enabled) {
		
		this.enabled = enabled;
	}
	
	public void setAnalogStick(int analogStick) {
		
		this.analogStick = analogStick;
	}
	
	public void setSensity(float sensity) {
		
		this.sensity = sensity;
	}
	
	public void mapLeftMouseButtonTo(int gamepadButton) {
		
		this.lmbMapping = gamepadButton;
	}
	
	public void mapRightMouseButtonTo(int gamepadButton) {
		
		this.rmbMapping = gamepadButton;
	}
	
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
	
	public boolean isEnabled() {
		
		return this.enabled;
	}
	
	public int getAnalogStick() {
		
		return this.analogStick;
	}
	
	public float getSensity() {
		
		return this.sensity;
	}
	
	public int getLeftMouseButtonMapping() {
		
		return this.lmbMapping;
	}
	
	public int getRightMouseButtonMapping() {
		
		return this.rmbMapping;
	}
	
	public int getMiddleMouseButtonMapping() {
		
		return this.mmbMapping;
	}
}
