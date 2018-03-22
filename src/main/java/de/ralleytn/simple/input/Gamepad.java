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
import java.util.ArrayList;
import java.util.List;

import de.ralleytn.simple.input.internal.Util;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Button;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.Rumbler;

// TODO
// ==== 18.03.2018 | Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
// How do I check if the gamepad is in analog or digital mode?
// ====

/**
 * Represents a gamepad.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class Gamepad extends Device {
	
	/**
	 * The maximum value the dead zone can have.
	 * @since 1.0.0
	 */
	public static final float MAX_DEAD_ZONE = 0.9999999F;
	
	private static final Identifier[] VALID_BUTTONS = {
			
		// PC/PlayStation | XBox
		Button._0,  Button.Y,
		Button._1,  Button.B,
		Button._2,  Button.A,
		Button._3,  Button.X,
		Button._4,  Button.LEFT_THUMB,
		Button._5,  Button.RIGHT_THUMB,
		Button._6,  Button.LEFT_THUMB2,
		Button._7,  Button.RIGHT_THUMB2,
		Button._8,  Button.SELECT,
		Button._9,  Button.START,
		Button._10, Button.LEFT_THUMB3,
		Button._11, Button.RIGHT_THUMB3,
		Button._12, Button.MODE
	};
	private static final int MAX_NUMBER_OF_BUTTONS = Gamepad.VALID_BUTTONS.length / 2;
	
	protected Rumbler[] rumblers;
	protected List<GamepadListener> listeners;
	protected Direction currentPOVDirection;
	protected int buttonCount;
	protected float axisY;
	protected float axisX;
	protected float axisRZ; // Y for right analog stick
	protected float axisZ; // X for right analog stick
	protected float deadZone;
	protected float cursorSensity;
	protected boolean controlCursorWithAnalogStick;
	protected int analogStickControllingCursor;
	protected boolean[] buttonsThatAreDown;
	
	Gamepad(Controller controller) {
		
		super(controller);

		this.buttonsThatAreDown = new boolean[Gamepad.MAX_NUMBER_OF_BUTTONS];
		this.rumblers = controller.getRumblers();
		this.listeners = new ArrayList<>();
		this.cursorSensity = 4.0F;
		
		for(Component component : controller.getComponents()) {
			
			if(Gamepad.isButton(component.getIdentifier())) {
				
				this.buttonCount++;
			}
		}
	}
	
	/**
	 * Sets the dead zone for the analog sticks.
	 * The dead zone is a value that prevents the {@link GamepadListener#onAnalogStickPush(GamepadEvent)} method from being called
	 * if the intensity with which the analog stick is pushed is not higher.
	 * @param value a value between {@code 0.0F} and <code>{@value #MAX_DEAD_ZONE}F</code> (using {@code 1.0F} would disable the analog sticks)
	 * @since 1.0.0
	 */
	public void setDeadZone(float value) {
		
		this.deadZone = value;
	}
	
	/**
	 * Adds a {@linkplain GamepadListener}.
	 * @param listener the {@linkplain GamepadListener}
	 * @since 1.0.0
	 */
	public void addGamepadListener(GamepadListener listener) {
		
		this.listeners.add(listener);
	}
	
	/**
	 * Removes a {@linkplain GamepadListener}.
	 * @param listener the {@linkplain GamepadListener}
	 * @since 1.0.0
	 */
	public void removeGamepadListener(GamepadListener listener) {
		
		this.listeners.remove(listener);
	}
	
	/**
	 * Removes a {@linkplain GamepadListener}.
	 * @param index the index of the {@linkplain GamepadListener}
	 * @since 1.0.0
	 */
	public void removeGamepadListener(int index) {
		
		this.listeners.remove(index);
	}
	
	/**
	 * @param index the index of the {@linkplain GamepadListener}
	 * @return the {@linkplain GamepadListener} with the given index
	 * @since 1.0.0
	 */
	public GamepadListener getGamepadListener(int index) {
		
		return this.listeners.get(index);
	}
	
	/**
	 * Rumbles the gamepad with the given intensity. Does nothing if {@link #canRumble()} returns {@code false}.
	 * @param intensity the intensity with wich the gamepad should rumble <br> ({@code 0.0F} = 0%, {@code 1.0F} = 100%)
	 * @since 1.0.0
	 */
	public void rumble(float intensity) {
		
		for(Rumbler rumbler : this.rumblers) {
			
			rumbler.rumble(intensity);
		}
	}
	
	/**
	 * @return {@code true} if this gamepad has a rumble function, else {@code false}
	 * @since 1.0.0
	 */
	public boolean canRumble() {
		
		return this.rumblers.length > 0;
	}
	
	/**
	 * @return the amount of buttons on the gamepad (extra buttons are not counted)
	 * @since 1.0.0
	 */
	public int getButtonCount() {
		
		return this.buttonCount;
	}

	/**
	 * 
	 * @param button
	 * @return
	 * @since 1.0.0
	 */
	public boolean isButtonDown(int button) {
		
		return this.buttonsThatAreDown[button];
	}
	
	/**
	 * @return the dead zone for the analog sticks
	 * @since 1.0.0
	 */
	public float getDeadZone() {
		
		return this.deadZone;
	}
	
	/**
	 * Sets the sensity with which the cursor is moved.
	 * @param sensity the sensity with which the cursor is moved
	 * @since 1.0.0
	 */
	public void setCursorSensity(float sensity) {
		
		this.cursorSensity = sensity;
	}
	
	/**
	 * Enables/Disables the ability to control the mouse cursor with an analog stick.
	 * @param flag {@code true} = enable, {@code false} = disable
	 * @since 1.0.0
	 */
	public void setControlCursorWithAnalogStick(boolean flag) {
		
		this.controlCursorWithAnalogStick = true;
	}
	
	/**
	 * Sets which analog stick should control the mouse cursor.
	 * @param analogStick can be either {@link GamepadEvent#ANALOG_STICK_LEFT} or {@link GamepadEvent#ANALOG_STICK_RIGHT}
	 * @since 1.0.0
	 */
	public void setAnalogStickControllingCursor(int analogStick) {
		
		this.analogStickControllingCursor = analogStick;
	}
	
	/**
	 * @return {@code true} if an analog stick is controlling the mouse cursor, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isCursorControlledByAnalogStick() {
		
		return this.controlCursorWithAnalogStick;
	}
	
	/**
	 * @return the sensity with which the cursor is moved
	 * @since 1.0.0
	 */
	public float getCursorSensity() {
		
		return this.cursorSensity;
	}
	
	/**
	 * @return the analog stick controlling the mouse cursor (can be either {@link GamepadEvent#ANALOG_STICK_LEFT} or {@link GamepadEvent#ANALOG_STICK_RIGHT})
	 * @since 1.0.0
	 */
	public int getAnalogStickControllingCursor() {
		
		return this.analogStickControllingCursor;
	}
	
	@Override
	protected void update() {
		
		if(this.controlCursorWithAnalogStick) {
			
			float x = 0;
			float y = 0;
			
			if(this.analogStickControllingCursor == GamepadEvent.ANALOG_STICK_LEFT) {
				
				x = this.axisX;
				y = this.axisY;
				
			} else if(this.analogStickControllingCursor == GamepadEvent.ANALOG_STICK_RIGHT) {
				
				x = this.axisZ;
				y = this.axisRZ;
			}
			
			Point cursorPosition = MouseInfo.getPointerInfo().getLocation();
			Util.setCursorPosition((int)(cursorPosition.x + (x * this.cursorSensity)), (int)(cursorPosition.y + (y * this.cursorSensity)));
		}
	}
	
	protected abstract void processInput(Identifier id, float value);

	@Override
	protected void onEvent(Event event) {
		
		Component component = event.getComponent();
		Identifier id = component.getIdentifier();
		float value = event.getValue();
		
		if(Axis.POV.equals(id)) {
			
			this.processPOVEvent(id, value);
			
		} else if(Gamepad.isButton(id)) {
			
			GamepadEvent gamepadEvent = new GamepadEvent(this, null, -1, id, 0.0F);
			
			if(value == 0.0F) {
				
				this.listeners.forEach(listener -> listener.onButtonRelease(gamepadEvent));
				
			} else {
				
				this.listeners.forEach(listener -> listener.onButtonPress(gamepadEvent));
			}
			
			this.buttonsThatAreDown[Util.getGamepadButtonByIdentifier(id)] = (value == 1.0F);
			       
		} else if(Axis.Y.equals(id)) {
			
			this.axisY = Util.isDead(value) ? 0.0F : value;
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_LEFT, value, id, this.axisX, this.axisY);
			
		} else if(Axis.X.equals(id)) {
			
			this.axisX = Util.isDead(value) ? 0.0F : value;
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_LEFT, value, id, this.axisX, this.axisY);
			
		} else if(Axis.RZ.equals(id) || Axis.RX.equals(id)) {
			
			this.axisRZ = Util.isDead(value) ? 0.0F : value;
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_RIGHT, value, id, this.axisZ, this.axisRZ);

		} else if(Axis.Z.equals(id) || Axis.RY.equals(id)) {
			
			this.axisZ = Util.isDead(value) ? 0.0F : value;
			this.processAnalogStickEvent(GamepadEvent.ANALOG_STICK_RIGHT, value, id, this.axisZ, this.axisRZ);
		}
	}
	
	protected final void processPOVEvent(Identifier id, float value) {
		
		Direction direction = Util.getPOVDirection(value);
		
		if(this.currentPOVDirection != direction) {
			
			if(this.currentPOVDirection != null) {
				
				this.listeners.forEach(listener -> {
					
					listener.onPOVRelease(new GamepadEvent(this, this.currentPOVDirection, -1, id, 0.0F));
					
					if(direction != null) {
						
						listener.onPOVPress(new GamepadEvent(this, direction, -1, id, 0.0F));
					}
				});
				
			} else {
				
				this.listeners.forEach(listener -> listener.onPOVPress(new GamepadEvent(this, direction, -1, id, 0.0F)));
			}
			
			this.currentPOVDirection = direction;
		}
	}
	
	protected final void processAnalogStickEvent(int analogStick, float value, Identifier id, float x, float y) {
		
		float intensity = Util.getIntensity(x, y);
		
		if(intensity > this.deadZone) {
			
			Direction direction = Util.getAnalogStickDirection(value, x, y);
			this.listeners.forEach(listener -> listener.onAnalogStickPush(new GamepadEvent(this, direction, analogStick, id, intensity)));
		}
	}
	
	private static final boolean isButton(Identifier id) {
		
		for(Identifier button : Gamepad.VALID_BUTTONS) {
			
			if(id.equals(button)) {
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	protected void remove() {
		
		this.listeners.forEach(listener -> listener.onRemove());
		this.stopListening();
	}
}
