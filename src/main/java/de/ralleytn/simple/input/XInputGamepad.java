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

import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Axis;
import de.ralleytn.simple.input.internal.XInputGamepadButtonMapping;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;

// FIXME
// ==== 23.03.2018 | Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
// Since jinput has no support for XInput yet, I can not get the values for the left and right trigger buttons individually.
// If someone has the time to make an XInput environment plugin for jinput, I would be really happy.
// ====

/**
 * Represents a gamepad that uses the XInput API.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class XInputGamepad extends Gamepad {

	private float axisX;
	private float axisY;
	private float axisRX;
	private float axisRY;
	private float axisZ;

	XInputGamepad(Controller controller) {
		
		super(controller, XInputGamepadButtonMapping.getDownButtonArraySize());
	}
	
	@Override
	protected int countButtons() {
		
		int buttonCount = 0;
		
		for(Component component : this.controller.getComponents()) {
			
			if(XInputGamepadButtonMapping.isValidButton(component.getIdentifier())) {
				
				buttonCount++;
			}
		}
		
		return buttonCount;
	}
	
	@Override
	protected void update() {
		
		this.mouseControl.updateCursorPosition(this.axisX, this.axisY, this.axisRX, this.axisRY);
	}

	@Override
	protected void onEvent(Event event) {
		
		Component component = event.getComponent();
		Identifier id = component.getIdentifier();
		float value = event.getValue();

		if(Axis.POV.equals(id)) {
			
			this.processPOVEvent(id, value);
			
		} else if(XInputGamepadButtonMapping.isValidButton(id)) {
			
			int button = XInputGamepadButtonMapping.getMap().get(id);
			this.processButtonEvent(button, value);
			       
		} else if(Axis.Y.equals(id)) {
			
			this.axisY = Gamepad.isDead(value) ? 0.0F : value;
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_LEFT, value, id, this.axisX, this.axisY);
			
		} else if(Axis.X.equals(id)) {
			
			this.axisX = Gamepad.isDead(value) ? 0.0F : value;
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_LEFT, value, id, this.axisX, this.axisY);
			
		} else if(Axis.RX.equals(id)) {
			
			this.axisRX = Gamepad.isDead(value) ? 0.0F : value;
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_RIGHT, value, id, this.axisRX, this.axisRY);

		} else if(Axis.RY.equals(id)) {
			
			this.axisRY = Gamepad.isDead(value) ? 0.0F : value;
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_RIGHT, value, id, this.axisRX, this.axisRY);
			
		} else if(Axis.Z.equals(id)) {
			
			this.axisZ = value;
		}
	}
	
	/**
	 * JInput doesn't have support for the XInput API (yet).
	 * Instead this method will return the sum of LT and RT.
	 * LT and RT can only have the values {@code -1.0F}, {@code 0.0F} and {@code 1.0F}.
	 * @return {@code -1.0F} if the LT button is down but the RT isn't, {@code 0.0F} if both triggers are down or not used and {@code 1.0F}
	 * 		   if the RT button is down but LT isn't
	 * @since 1.0.0
	 */
	public float getTriggerValue() {
		
		return this.axisZ;
	}
}
