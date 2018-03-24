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
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;

import de.ralleytn.simple.input.internal.MouseButtonMapping;
import de.ralleytn.simple.input.internal.Util;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Controller;
import net.java.games.input.Event;

/**
 * Represents a mouse.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class Mouse extends Device {

	private List<MouseListener> listeners;
	private boolean[] buttonsDown;
	private int buttonCount;
	
	Mouse(Controller controller) {
		
		super(controller);
		
		this.listeners = new ArrayList<>();
		this.buttonsDown = new boolean[MouseButtonMapping.getDownButtonArraySize()];
		
		for(Component component : controller.getComponents()) {
			
			if(MouseButtonMapping.isValidButton(component.getIdentifier())) {
				
				this.buttonCount++;
			}
		}
	}
	
	public static final void click(int button) {
		
		int mask = InputEvent.getMaskForButton(button);
		Util.ROBOT.mousePress(mask);
		Util.ROBOT.mouseRelease(mask);
	}
	
	/**
	 * Sets the mouse cursor position.
	 * @param x X coordinate in pixel
	 * @param y Y coordinate in pixel
	 * @since 1.0.0
	 */
	public static void setCursorPosition(int x, int y) {
		
		Util.ROBOT.mouseMove(x, y);
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
		
		if(MouseButtonMapping.isValidButton(id)) {

			int button = MouseButtonMapping.getMap().get(id);
			this.buttonsDown[button] = (value == 1.0F);
			this.processButtonEvent(id, this.buttonsDown[button]);
			
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
		
		int button = MouseButtonMapping.getMap().get(id);
		
		if(buttonDown) {
			
			this.listeners.forEach(listener -> listener.onClick(new MouseEvent(this, 0, 0, 0, button)));
			
		} else {
			
			this.listeners.forEach(listener -> listener.onRelease(new MouseEvent(this, 0, 0, 0, button)));
		}
	}
	
	private final void processMovementEvent(float delta) {
		
			   if(this.buttonsDown[MouseEvent.BUTTON_LEFT])   {this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, 0, delta, 0, MouseButtonMapping.getMap().get(Identifier.Button.LEFT))));
		} else if(this.buttonsDown[MouseEvent.BUTTON_RIGHT])  {this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, 0, delta, 0, MouseButtonMapping.getMap().get(Identifier.Button.RIGHT))));
		} else if(this.buttonsDown[MouseEvent.BUTTON_MIDDLE]) {this.listeners.forEach(listener -> listener.onDrag(new MouseEvent(this, 0, delta, 0, MouseButtonMapping.getMap().get(Identifier.Button.MIDDLE))));
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
	
	public boolean isButtonDown(int button) {
		
		return this.buttonsDown[button];
	}
}
