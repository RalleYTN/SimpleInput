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
public class Gamepad extends Device {
	
	/**
	 * The maximum value the dead zone can have.
	 * @since 1.0.0
	 */
	public static final float MAX_DEAD_ZONE = 0.9999999F;
	
	private static final Identifier[] VALID_BUTTONS = {
			
		// PC/PlayStation | XBox
		Button._0, Button.Y,
		Button._1, Button.B,
		Button._2, Button.A,
		Button._3, Button.X,
		Button._4, Button.LEFT_THUMB,
		Button._5, Button.RIGHT_THUMB,
		Button._6, Button.LEFT_THUMB2,
		Button._7, Button.RIGHT_THUMB2,
		Button._8, Button.SELECT,
		Button._9, Button.START,
		Button._10, Button.LEFT_THUMB3,
		Button._11, Button.RIGHT_THUMB3,
		Button._12, Button.MODE
	};
	
	private Rumbler[] rumblers;
	private List<GamepadListener> listeners;
	private Direction currentPOVDirection;
	private int buttonCount;
	private float axisY;
	private float axisX;
	private float axisRZ; // Y for right analog stick
	private float axisZ; // X for right analog stick
	private float deadZone;
	private float cursorSensity;
	private boolean controlCursorWithAnalogStick;
	private int analogStickControllingCursor;
	
	private boolean xDown, yDown, aDown, bDown;
	private boolean selectDown, modeDown, startDown;
	private boolean l1Down, l2Down, l3Down;
	private boolean r1Down, r2Down, r3Down;
	
	/**
	 * @param controller the {@linkplain Controller} that should be wrapped
	 * @since 1.0.0
	 */
	public Gamepad(Controller controller) {
		
		super(controller);

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
	 * @return {@code true} if the X/4 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isXDown() {
		
		return this.xDown;
	}
	
	/**
	 * @return {@code true} if the Y/1 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isYDown() {
		
		return this.yDown;
	}
	
	/**
	 * @return {@code true} if the A/3 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isADown() {
		
		return this.aDown;
	}
	
	/**
	 * @return {@code true} if the X/2 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isBDown() {
		
		return this.bDown;
	}
	
	/**
	 * @return {@code true} if the L1 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isL1Down() {
		
		return this.l1Down;
	}
	
	/**
	 * @return {@code true} if the L2 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isL2Down() {
		
		return this.l2Down;
	}
	
	/**
	 * @return {@code true} if the L3 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isL3Down() {
		
		return this.l3Down;
	}
	
	/**
	 * @return {@code true} if the R1 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isR1Down() {
		
		return this.r1Down;
	}
	
	/**
	 * @return {@code true} if the R2 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isR2Down() {
		
		return this.r2Down;
	}
	
	/**
	 * @return {@code true} if the R3 button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isR3Down() {
		
		return this.r3Down;
	}
	
	/**
	 * @return {@code true} if the MODE button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isModeDown() {
		
		return this.modeDown;
	}
	
	/**
	 * @return {@code true} if the SELECT button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isSelectDown() {
		
		return this.selectDown;
	}
	
	/**
	 * @return {@code true} if the START button is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isStartDown() {
		
		return this.startDown;
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
			
			this.updateButtonDownList(id, value);
			       
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
	
	private final void updateButtonDownList(Identifier id, float value) {
		
		       if(Button._0.equals(id)  || Button.Y.equals(id))            {this.yDown      = (value == 1.0F);
		} else if(Button._1.equals(id)  || Button.B.equals(id))            {this.bDown      = (value == 1.0F);
		} else if(Button._2.equals(id)  || Button.A.equals(id))            {this.aDown      = (value == 1.0F);
		} else if(Button._3.equals(id)  || Button.X.equals(id))            {this.xDown      = (value == 1.0F);
		} else if(Button._4.equals(id)  || Button.LEFT_THUMB.equals(id))   {this.l1Down     = (value == 1.0F);
		} else if(Button._5.equals(id)  || Button.RIGHT_THUMB.equals(id))  {this.r1Down     = (value == 1.0F);
		} else if(Button._6.equals(id)  || Button.LEFT_THUMB2.equals(id))  {this.l2Down     = (value == 1.0F);
		} else if(Button._7.equals(id)  || Button.RIGHT_THUMB2.equals(id)) {this.r2Down     = (value == 1.0F);
		} else if(Button._8.equals(id)  || Button.SELECT.equals(id))       {this.selectDown = (value == 1.0F);
		} else if(Button._9.equals(id)  || Button.START.equals(id))        {this.startDown  = (value == 1.0F);
		} else if(Button._10.equals(id) || Button.LEFT_THUMB3.equals(id))  {this.l3Down     = (value == 1.0F);
		} else if(Button._11.equals(id) || Button.RIGHT_THUMB3.equals(id)) {this.r3Down     = (value == 1.0F);
		} else if(Button._12.equals(id) || Button.MODE.equals(id))         {this.modeDown   = (value == 1.0F);
		}
	}
	
	private final void processPOVEvent(Identifier id, float value) {
		
		Direction direction = Gamepad.getPOVDirection(value);
		
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
	
	private final void processAnalogStickEvent(int analogStick, float value, Identifier id, float x, float y) {
		
		float intensity = Gamepad.getIntensity(x, y);
		
		if(intensity > this.deadZone) {
			
			Direction direction = Gamepad.getAnalogStickDirection(value, x, y);
			this.listeners.forEach(listener -> listener.onAnalogStickPush(new GamepadEvent(this, direction, analogStick, id, intensity)));
		}
	}
	
	private static final Direction getPOVDirection(float value) {
		
		Direction direction = null;
		
		       if(value == 0.125F) {direction = Direction.NORTH_WEST;
		} else if(value == 0.25F)  {direction = Direction.NORTH;
		} else if(value == 0.375F) {direction = Direction.NORTH_EAST;
		} else if(value == 0.5F)   {direction = Direction.EAST;
		} else if(value == 0.625F) {direction = Direction.SOUTH_EAST;
		} else if(value == 0.75F)  {direction = Direction.SOUTH;
		} else if(value == 0.875F) {direction = Direction.SOUTH_WEST;
		} else if(value == 1.0F)   {direction = Direction.WEST;
		}
		
		return direction;
	}
	
	private static final Direction getAnalogStickDirection(float value, float x, float y) {
		
		Direction direction = null;
		
		if(value != 0.0F) {
			
			double angle = Math.toDegrees(Math.atan2(y, x));
			
			       if(angle == -90.0F) {direction = Direction.NORTH;
			} else if(angle == 0.0F)   {direction = Direction.EAST;
			} else if(angle == 180.0F) {direction = Direction.WEST;
			} else if(angle == 90.0F)  {direction = Direction.SOUTH;
			} else if(angle < -90.0F)  {direction = Direction.NORTH_WEST;
			} else if(angle < 0.0F)    {direction = Direction.NORTH_EAST;
			} else if(angle > 90.0F)   {direction = Direction.SOUTH_WEST;
			} else if(angle > 0.0F)    {direction = Direction.SOUTH_EAST;
			}
		}
		
		return direction;
	}

	private static final float getIntensity(float x, float y) {
		
		double velocity = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	    return (float)(velocity > 1.0F ? 1.0F : velocity);
	}
	
	private static final boolean isDead(float value) {
		
		return value < 0.1F && value > -0.1F;
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
