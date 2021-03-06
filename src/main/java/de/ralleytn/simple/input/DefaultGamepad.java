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
import de.ralleytn.simple.input.internal.DefaultGamepadButtonMapping;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;

/**
 * Represents a gamepad that uses the DirectInput API.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.1.0
 * @since 1.0.0
 */
public class DefaultGamepad extends Gamepad {

	private float axisX;
	private float axisY;
	private float axisZ;
	private float axisRZ;

	DefaultGamepad(Controller controller) {
		
		super(controller, DefaultGamepadButtonMapping.getDownButtonArraySize(), new MouseControl());
	}
	
	@Override
	protected int countButtons() {
		
		int buttonCount = 0;
		
		for(Component component : this.controller.getComponents()) {
			
			if(DefaultGamepadButtonMapping.isValidButton(component.getIdentifier())) {
				
				buttonCount++;
			}
		}
		
		return buttonCount;
	}
	
	@Override
	protected void update() {
		
		this.mouseControl.updateCursorPosition(this.axisX, this.axisY, this.axisZ, this.axisRZ);
	}
	
	@Override
	protected void onEvent(Event event) {
		
		Component component = event.getComponent();
		Identifier id = component.getIdentifier();
		float value = event.getValue();
		
		if(Axis.POV.equals(id)) {
			
			this.processPOVEvent(id, value);
			
		} else if(DefaultGamepadButtonMapping.isValidButton(id)) {
			
			int button = DefaultGamepadButtonMapping.getMap().get(id);
			this.processButtonEvent(button, value);
			       
		} else if(Axis.Y.equals(id)) {
			System.out.println(component.isAnalog());
			this.axisY = Gamepad.isDead(value) ? 0.0F : value;
			Direction direction = DefaultGamepadButtonMapping.getAnalogStickDirection(value, this.axisX, this.axisY);
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_LEFT, value, id, this.axisX, this.axisY, direction);
			
		} else if(Axis.X.equals(id)) {
			
			this.axisX = Gamepad.isDead(value) ? 0.0F : value;
			Direction direction = DefaultGamepadButtonMapping.getAnalogStickDirection(value, this.axisX, this.axisY);
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_LEFT, value, id, this.axisX, this.axisY, direction);
			
		} else if(Axis.RZ.equals(id)) {
			
			this.axisRZ = Gamepad.isDead(value) ? 0.0F : value;
			Direction direction = DefaultGamepadButtonMapping.getAnalogStickDirection(value, this.axisZ, this.axisRZ);
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_RIGHT, value, id, this.axisZ, this.axisRZ, direction);

		} else if(Axis.Z.equals(id)) {
			
			this.axisZ = Gamepad.isDead(value) ? 0.0F : value;
			Direction direction = DefaultGamepadButtonMapping.getAnalogStickDirection(value, this.axisZ, this.axisRZ);
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_RIGHT, value, id, this.axisZ, this.axisRZ, direction);
		}
	}
}
