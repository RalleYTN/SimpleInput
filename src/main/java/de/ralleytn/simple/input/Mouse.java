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
import java.util.ArrayList;
import java.util.List;

import de.ralleytn.simple.input.internal.Util;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component.Identifier.Button;
import net.java.games.input.Controller;
import net.java.games.input.Event;

/**
 * Represents a mouse.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class Mouse extends Device {

	private static final Identifier[] VALID_BUTTONS = {
			
		Button.LEFT,
		Button.MIDDLE,
		Button.RIGHT
	};
	
	private List<MouseListener> listeners;
	private boolean leftDown;
	private boolean rightDown;
	private boolean middleDown;
	private int buttonCount;
	
	Mouse(Controller controller) {
		
		super(controller);
		
		this.listeners = new ArrayList<>();
		
		for(Component component : controller.getComponents()) {
			
			if(Mouse.isButton(component.getIdentifier())) {
				
				this.buttonCount++;
			}
		}
	}
	
	/**
	 * Sets the mouse cursor position.
	 * @param x X coordinate in pixel
	 * @param y Y coordinate in pixel
	 * @since 1.0.0
	 */
	public static void setCursorPosition(int x, int y) {
		
		Util.setCursorPosition(x, y);
	}
	
	@Override
	protected void remove() {
		
		this.listeners.forEach(listener -> listener.onRemove());
		this.stopListening();
	}
	
	/**
	 * Adds a {@linkplain MouseListener}.
	 * @param listener the {@linkplain MouseListener}
	 * @since 1.0.0
	 */
	public void addMouseListener(MouseListener listener) {
		
		this.listeners.add(listener);
	}
	
	/**
	 * Removes a {@linkplain MouseListener}.
	 * @param listener the {@linkplain MouseListener}
	 * @since 1.0.0
	 */
	public void removeMouseListener(MouseListener listener) {
		
		this.listeners.remove(listener);
	}
	
	/**
	 * Removes a {@linkplain MouseListener}.
	 * @param index the index of the {@linkplain MouseListener}
	 * @since 1.0.0
	 */
	public void removeMouseListener(int index) {
		
		this.listeners.remove(index);
	}
	
	/**
	 * @param index the index of the {@linkplain MouseListener}
	 * @return a {@linkplain MouseListener}
	 * @since 1.0.0
	 */
	public MouseListener getMouseListener(int index) {
		
		return this.listeners.get(index);
	}

	@Override
	protected void onEvent(Event event) {
		
		Component component = event.getComponent();
		Identifier id = component.getIdentifier();
		float value = event.getValue();
		
		if(Button.LEFT.equals(id)) {

			this.leftDown = (value == 1.0F);
			this.processButtonEvent(id, this.leftDown);
			
		} else if(Button.RIGHT.equals(id)) {
			
			this.rightDown = (value == 1.0F);
			this.processButtonEvent(id, this.rightDown);
			
		} else if(Button.MIDDLE.equals(id)) {
			
			this.middleDown = (value == 1.0F);
			this.processButtonEvent(id, this.middleDown);
			
		} else if(Axis.X.equals(id)) {
			
			float deltaX = event.getValue();
			this.processMovementEvent(deltaX);
			
		} else if(Axis.Y.equals(id)) {
			
			float deltaY = event.getValue();
			this.processMovementEvent(deltaY);
			
		} else if(Axis.Z.equals(id)) {
			
			this.listeners.forEach(listener -> listener.onScroll(new MouseEvent(this, 0, 0, event.getValue(), -1)));
		}
	}
	
	private final void processButtonEvent(Identifier id, boolean buttonDown) {
		
		int button = Mouse.getButtonByIdentifier(id);
		
		if(buttonDown) {
			
			this.listeners.forEach(listener -> listener.onClick(new MouseEvent(this, 0, 0, 0, button)));
			
		} else {
			
			this.listeners.forEach(listener -> listener.onRelease(new MouseEvent(this, 0, 0, 0, button)));
		}
	}
	
	private final void processMovementEvent(float delta) {
		
			   if(this.leftDown)   {this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, 0, delta, 0, Mouse.getButtonByIdentifier(Identifier.Button.LEFT))));
		} else if(this.rightDown)  {this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, 0, delta, 0, Mouse.getButtonByIdentifier(Identifier.Button.RIGHT))));
		} else if(this.middleDown) {this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, 0, delta, 0, Mouse.getButtonByIdentifier(Identifier.Button.MIDDLE))));
		} else {
			
			this.listeners.forEach(listener -> listener.onMove(new MouseEvent(this, 0, delta, 0, -1)));
		}
	}
	
	/**
	 * @return the number of buttons on this mouse (extra buttons are not counted)
	 * @since 1.0.0
	 */
	public int getButtonCount() {
		
		return this.buttonCount;
	}
	
	/**
	 * @return the X position of the cursor on the screen
	 * @since 1.0.0
	 */
	public static int getX() {
		
		return MouseInfo.getPointerInfo().getLocation().x;
	}
	
	/**
	 * @return the Y position of the cursor on the screen
	 * @since 1.0.0
	 */
	public static int getY() {
		
		return MouseInfo.getPointerInfo().getLocation().y;
	}
	
	/**
	 * @return {@code true} if the left mouse button is currently pressed, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isLeftDown() {
		
		return this.leftDown;
	}
	
	/**
	 * @return {@code true} if the right mouse button is currently pressed, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isRightDown() {
		
		return this.rightDown;
	}
	
	/**
	 * @return {@code true} if the middle mouse button is currently pressed, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isMiddleDown() {
		
		return this.middleDown;
	}
	
	private static final int getButtonByIdentifier(Identifier id) {
		
		return id == Identifier.Button.LEFT ? MouseEvent.BUTTON_LEFT :
			   id == Identifier.Button.MIDDLE ? MouseEvent.BUTTON_MIDDLE :
			   id == Identifier.Button.RIGHT ? MouseEvent.BUTTON_RIGHT :
		       -1;
	}
	
	private static final boolean isButton(Identifier id) {
		
		for(Identifier button : Mouse.VALID_BUTTONS) {
			
			if(button.equals(id)) {
				
				return true;
			}
		}
		
		return false;
	}
}
