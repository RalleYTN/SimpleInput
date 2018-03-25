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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
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
	
	protected Rumbler[] rumblers;
	protected List<GamepadListener> listeners;
	protected Direction currentPOVDirection;
	protected int buttonCount;
	protected float deadZone;
	protected MouseControl mouseControl;
	protected boolean[] buttonsThatAreDown;
	
	Gamepad(Controller controller, int buttonDownArraySize) {
		
		super(controller);

		this.buttonsThatAreDown = new boolean[buttonDownArraySize];
		this.rumblers = controller.getRumblers();
		this.listeners = new ArrayList<>();
		this.mouseControl = new MouseControl();
		this.buttonCount = this.countButtons();
	}
	
	protected abstract int countButtons();
	
	/**
	 * @return an unmodifiable list of the {@linkplain GamepadListener}s that are attached to this gamepad.
	 * @since 1.0.0
	 */
	public List<GamepadListener> getGamepadListeners() {
		
		return Collections.unmodifiableList(this.listeners);
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
	 * Checks if the given button is down.
	 * @param button the button
	 * @return {@code true} if the button is down, else {@code false}
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
	 * @return the {@linkplain MouseControl} instance that is attached to this gamepad.
	 * @since 1.0.0
	 */
	public MouseControl getMouseControl() {
		
		return this.mouseControl;
	}
	
	protected final void processButtonEvent(int button, float value) {
		
		GamepadEvent gamepadEvent = new GamepadEvent(this, null, -1, button, 0.0F);
		
		if(value == 0.0F) {
			
			this.listeners.forEach(listener -> listener.onButtonRelease(gamepadEvent));
			this.mouseControl.processReleaseButtonEvent(button);
			
		} else {
			
			this.listeners.forEach(listener -> listener.onButtonPress(gamepadEvent));
			this.mouseControl.processPressButtonEvent(button);
		}
		
		this.buttonsThatAreDown[button] = (value == 1.0F);
	}
	
	protected final void processPOVEvent(Identifier id, float value) {
		
		Direction direction = Gamepad.getPOVDirection(value);
		
		if(this.currentPOVDirection != direction) {
			
			if(this.currentPOVDirection != null) {
				
				this.listeners.forEach(listener -> {
					
					listener.onPOVRelease(new GamepadEvent(this, this.currentPOVDirection, -1, -1, 0.0F));
					
					if(direction != null) {
						
						listener.onPOVPress(new GamepadEvent(this, direction, -1, -1, 0.0F));
					}
				});
				
			} else {
				
				this.listeners.forEach(listener -> listener.onPOVPress(new GamepadEvent(this, direction, -1, -1, 0.0F)));
			}
			
			this.currentPOVDirection = direction;
		}
	}
	
	protected final void processAnalogStickEvent(int analogStick, float value, Identifier id, float x, float y) {
		
		float intensity = Gamepad.getIntensity(x, y);
		
		if(intensity > this.deadZone) {
			
			Direction direction = Gamepad.getAnalogStickDirection(value, x, y);
			this.listeners.forEach(listener -> listener.onAnalogStickPush(new GamepadEvent(this, direction, analogStick, -1, intensity)));
		}
	}

	private static final float getIntensity(float x, float y) {
		
		double velocity = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	    return (float)(velocity > 1.0F ? 1.0F : velocity);
	}

	protected static final boolean isDead(float value) {
		
		return value < 0.1F && value > -0.1F;
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

	@Override
	protected void remove() {
		
		this.listeners.forEach(listener -> listener.onRemove());
		this.stopListening();
	}
}
