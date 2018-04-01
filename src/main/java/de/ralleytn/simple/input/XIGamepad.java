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

import static de.ralleytn.simple.input.XIGamepadEvent.*;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Axis;
import de.ralleytn.simple.input.internal.Util;
import de.ralleytn.simple.input.internal.XIGamepadButtonMapping;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;

/**
 * Represents a gamepad that uses the XInput API.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.1.0
 * @since 1.0.0
 */
public class XIGamepad extends Gamepad {

	private float axisX;
	private float axisY;
	private float axisRX;
	private float axisRY;
	private float axisZ;
	private float axisRZ;

	XIGamepad(Controller controller) {
		
		super(controller, XIGamepadButtonMapping.getDownButtonArraySize(Util.hasNoNavigation(controller)), new XIMouseControl());
	}
	
	@Override
	protected int countButtons() {
		
		int buttonCount = 0;
		
		for(Component component : this.controller.getComponents()) {
			
			if(XIGamepadButtonMapping.isValidButton(component.getIdentifier(), this.hasNoNavigation())) {
				
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
			
		} else if(XIGamepadButtonMapping.isValidButton(id, this.hasNoNavigation())) {
			
			int button = XIGamepadButtonMapping.getMap(this.hasNoNavigation()).get(id);
			this.processButtonEvent(button, value);
			       
		} else if(Axis.Y.equals(id)) {
			
			this.axisY = Gamepad.isDead(value) ? 0.0F : value;
			Direction direction = XIGamepadButtonMapping.getAnalogStickDirection(value, this.axisX, this.axisY);
			
			if(direction != null) {
				
				this.processAnalogStickEvent(ANALOG_STICK_LEFT, value, id, this.axisX, this.axisY, direction);
			}
			
		} else if(Axis.X.equals(id)) {
			
			this.axisX = Gamepad.isDead(value) ? 0.0F : value;
			Direction direction = XIGamepadButtonMapping.getAnalogStickDirection(value, this.axisX, this.axisY);
			
			if(direction != null) {
				
				this.processAnalogStickEvent(ANALOG_STICK_LEFT, value, id, this.axisX, this.axisY, direction);
			}
			
		} else if(Axis.RX.equals(id)) {
			
			this.axisRX = Gamepad.isDead(value) ? 0.0F : value;
			Direction direction = XIGamepadButtonMapping.getAnalogStickDirection(value, this.axisRX, this.axisRY);
			
			if(direction != null) {
				
				this.processAnalogStickEvent(ANALOG_STICK_RIGHT, value, id, this.axisRX, this.axisRY, direction);
			}

		} else if(Axis.RY.equals(id)) {
			
			this.axisRY = Gamepad.isDead(value) ? 0.0F : value;
			Direction direction = XIGamepadButtonMapping.getAnalogStickDirection(value, this.axisRX, this.axisRY);
			
			if(direction != null) {
				
				this.processAnalogStickEvent(ANALOG_STICK_RIGHT, value, id, this.axisRX, this.axisRY, direction);
			}
			
		} else if(Axis.Z.equals(id)) {
			
			this.axisZ = value;
			this.processTriggerEvent(TRIGGER_LEFT, value);
			
		} else if(Axis.RZ.equals(id)) {
			
			this.axisRZ = value;
			this.processTriggerEvent(TRIGGER_RIGHT, value);
		}
	}
	
	private final void processTriggerEvent(int trigger, float value) {
		
		this.listeners.forEach(listener -> {
			
			if(listener instanceof XIGamepadListener) {
				
				((XIGamepadListener)listener).onTriggerPush(new XIGamepadEvent(this, trigger, value));
				
				if(value == 1.0F) {
					
					listener.onButtonPress(new XIGamepadEvent(this, trigger == TRIGGER_LEFT ? BUTTON_LT : BUTTON_RT, true));
					
				} else if(value == 0.0F) {
					
					listener.onButtonRelease(new XIGamepadEvent(this, trigger == TRIGGER_LEFT ? BUTTON_LT : BUTTON_RT, false));
				}
			}
		});
	}
	
	/**
	 * <strike>JInput doesn't have support for the XInput API (yet).
	 * Instead</strike> this method will return the sum of LT and RT.
	 * LT and RT can only have the values {@code -1.0F}, {@code 0.0F} and {@code 1.0F}.
	 * @return {@code -1.0F} if the LT button is down but the RT isn't, {@code 0.0F} if both triggers are down or not used and {@code 1.0F}
	 * 		   if the RT button is down but LT isn't
	 * @since 1.0.0
	 * @deprecated since 1.1.0; see {@linkplain XIGamepadListener#onTriggerPush(GamepadEvent)}
	 */
	@Deprecated
	public float getTriggerValue() {
		
		float z = this.axisZ == 1.0F ? -1.0F : 0.0F;
		float rz = this.axisRZ == 1.0F ? this.axisRZ : 0.0F;
		
		return z + rz;
	}
	
	/**
	 * @return {@code true} if the START and BACK buttons and the POV are missing, else {@code false}
	 * @since 1.1.0
	 */
	public boolean hasNoNavigation() {
		
		return Util.hasNoNavigation(this.controller);
	}
}
