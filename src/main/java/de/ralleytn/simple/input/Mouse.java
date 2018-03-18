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
	
	/**
	 * @param controller the {@linkplain Controller} that should be wrapped
	 * @since 1.0.0
	 */
	public Mouse(Controller controller) {
		
		super(controller);
		
		this.listeners = new ArrayList<>();
		
		for(Component component : controller.getComponents()) {
			
			if(Mouse.isButton(component.getIdentifier())) {
				
				this.buttonCount++;
			}
		}
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
	protected void update() {}

	@Override
	protected void onEvent(Event event) {
		
		Component component = event.getComponent();
		Identifier id = component.getIdentifier();
		float value = event.getValue();
		
		if(id == Button.LEFT) {

			this.leftDown = value == 1.0F;
			
			if(this.leftDown) {
				
				this.listeners.forEach(listener -> listener.onClick(new MouseEvent(this, 0, 0, 0, id)));
				
			} else {
				
				this.listeners.forEach(listener -> listener.onRelease(new MouseEvent(this, 0, 0, 0, id)));
			}
			
		} else if(id == Button.RIGHT) {
			
			this.rightDown = value == 1.0F;
			
			if(this.rightDown) {
				
				this.listeners.forEach(listener -> listener.onClick(new MouseEvent(this, 0, 0, 0, id)));
				
			} else {
				
				this.listeners.forEach(listener -> listener.onRelease(new MouseEvent(this, 0, 0, 0, id)));
			}
			
		} else if(id == Button.MIDDLE) {
			
			this.middleDown = value == 1.0F;
			
			if(this.middleDown) {
				
				this.listeners.forEach(listener -> listener.onClick(new MouseEvent(this, 0, 0, 0, id)));
				
			} else {
				
				this.listeners.forEach(listener -> listener.onRelease(new MouseEvent(this, 0, 0, 0, id)));
			}
			
		} else if(id == Axis.X) {
			
			float deltaX = event.getValue();
			
			if(this.leftDown) {
				
				this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, deltaX, 0, 0, Identifier.Button.LEFT)));
				
			} else if(this.rightDown) {
				
				this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, deltaX, 0, 0, Identifier.Button.RIGHT)));
				
			} else if(this.middleDown) {
				
				this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, deltaX, 0, 0, Identifier.Button.MIDDLE)));
				
			} else {
				
				this.listeners.forEach(listener -> listener.onMove(new MouseEvent(this, deltaX, 0, 0, null)));
			}
			
		} else if(id == Axis.Y) {
			
			float deltaY = event.getValue();
			
			if(this.leftDown) {
				
				this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, 0, deltaY, 0, Identifier.Button.LEFT)));
				
			} else if(this.rightDown) {
				
				this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, 0, deltaY, 0, Identifier.Button.RIGHT)));
				
			} else if(this.middleDown) {
				
				this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, 0, deltaY, 0, Identifier.Button.MIDDLE)));
				
			} else {
				
				this.listeners.forEach(listener -> listener.onMove(new MouseEvent(this, 0, deltaY, 0, null)));
			}
			
		} else if(id == Axis.Z) {
			
			this.listeners.forEach(listener -> listener.onScroll(new MouseEvent(this, 0, 0, event.getValue(), null)));
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
	public int getX() {
		
		return MouseInfo.getPointerInfo().getLocation().x;
	}
	
	/**
	 * @return the Y position of the cursor on the screen
	 * @since 1.0.0
	 */
	public int getY() {
		
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
	
	private static final boolean isButton(Identifier id) {
		
		for(Identifier button : Mouse.VALID_BUTTONS) {
			
			if(button == id) {
				
				return true;
			}
		}
		
		return false;
	}
}
