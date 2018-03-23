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

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class DirectInputGamepad extends Gamepad {

	private float axisX;
	private float axisY;
	private float axisZ;
	private float axisRZ;

	DirectInputGamepad(Controller controller) {
		
		super(controller);
	}
	
	@Override
	protected void update() {
		
		this.updateCursorPosition(this.axisX, this.axisY, this.axisZ, this.axisRZ);
	}
	
	@Override
	protected void onEvent(Event event) {
		
		Component component = event.getComponent();
		Identifier id = component.getIdentifier();
		float value = event.getValue();
		
		if(Axis.POV.equals(id)) {
			
			this.processPOVEvent(id, value);
			
		} else if(Gamepad.isButton(id)) {
			
			int button = DirectInputGamepad.getGamepadButtonByIdentifier(id);
			this.processButtonEvent(button, value);
			       
		} else if(Axis.Y.equals(id)) {
			
			this.axisY = Gamepad.isDead(value) ? 0.0F : value;
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_LEFT, value, id, this.axisX, this.axisY);
			
		} else if(Axis.X.equals(id)) {
			
			this.axisX = Gamepad.isDead(value) ? 0.0F : value;
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_LEFT, value, id, this.axisX, this.axisY);
			
		} else if(Axis.RZ.equals(id)) {
			
			this.axisRZ = Gamepad.isDead(value) ? 0.0F : value;
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_RIGHT, value, id, this.axisZ, this.axisRZ);

		} else if(Axis.Z.equals(id)) {
			
			this.axisZ = Gamepad.isDead(value) ? 0.0F : value;
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_RIGHT, value, id, this.axisZ, this.axisRZ);
		}
	}

	private static final int getGamepadButtonByIdentifier(Identifier id) {
		
		return id == Identifier.Button._0  || id == Identifier.Button.Y            ? GamepadEvent.BUTTON_Y :
			   id == Identifier.Button._1  || id == Identifier.Button.B            ? GamepadEvent.BUTTON_B :
			   id == Identifier.Button._2  || id == Identifier.Button.A            ? GamepadEvent.BUTTON_A :
			   id == Identifier.Button._3  || id == Identifier.Button.X            ? GamepadEvent.BUTTON_X :
			   id == Identifier.Button._4  || id == Identifier.Button.LEFT_THUMB   ? GamepadEvent.BUTTON_L1 :
			   id == Identifier.Button._5  || id == Identifier.Button.RIGHT_THUMB  ? GamepadEvent.BUTTON_R1 :
			   id == Identifier.Button._6  || id == Identifier.Button.LEFT_THUMB2  ? GamepadEvent.BUTTON_L2 :
			   id == Identifier.Button._7  || id == Identifier.Button.RIGHT_THUMB2 ? GamepadEvent.BUTTON_R2 :
			   id == Identifier.Button._8  || id == Identifier.Button.SELECT       ? GamepadEvent.BUTTON_SELECT :
			   id == Identifier.Button._9  || id == Identifier.Button.START        ? GamepadEvent.BUTTON_START :
			   id == Identifier.Button._10 || id == Identifier.Button.LEFT_THUMB3  ? GamepadEvent.BUTTON_L3 :
			   id == Identifier.Button._11 || id == Identifier.Button.RIGHT_THUMB3 ? GamepadEvent.BUTTON_R3 :
			   id == Identifier.Button._12 || id == Identifier.Button.MODE         ? GamepadEvent.BUTTON_MODE :
			   -1;
	}
}
