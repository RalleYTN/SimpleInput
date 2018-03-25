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

import java.awt.event.KeyEvent;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.ralleytn.simple.input.internal.KeyboardKeyMapping;
import de.ralleytn.simple.input.internal.Util;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.Event;

/**
 * Represents a keyboard.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class Keyboard extends Device {
	
	private List<KeyboardListener> listeners;
	private boolean[] downKeys;

	Keyboard(Controller controller) {
		
		super(controller);
		
		this.listeners = new ArrayList<>();
		this.downKeys = new boolean[KeyboardKeyMapping.getDownKeyArraySize()];
	}
	
	/**
	 * Uses the {@linkplain Robot} class to type the given text.
	 * Only works for simple letters with upper and lower case and a period.
	 * @param text a text
	 * @since 1.0.0
	 */
	public static final void type(String text) {
		
		for(char character : text.toCharArray()) {
			
			boolean upperCase = Character.isUpperCase(character);
			int keyCode = KeyEvent.getExtendedKeyCodeForChar(character);
			
			if(KeyEvent.CHAR_UNDEFINED != keyCode) {
				
				if(upperCase) {
					
					Keyboard.press(KeyboardEvent.KEY_SHIFT);
					Keyboard.type(keyCode);
					Keyboard.release(KeyboardEvent.KEY_SHIFT);
					
				} else {
					
					Keyboard.type(keyCode);
				}
			}
		}
	}
	
	/**
	 * Uses the {@linkplain Robot} class to type the given key.
	 * @param key the key
	 * @since 1.0.0
	 */
	public static final void type(int key) {

		Keyboard.press(key);
		Keyboard.release(key);
	}
	
	/**
	 * Uses the {@linkplain Robot} class to press and hold a key.
	 * Don't forget to call {@link #release(int)}.
	 * @param key the key
	 * @since 1.0.0
	 */
	public static final void press(int key) {

		Util.ROBOT.keyPress(key);
	}
	
	/**
	 * Releases a key that is held down by the {@link #press(int)} method.
	 * @param key the key
	 * @since 1.0.0
	 */
	public static final void release(int key) {
		
		Util.ROBOT.keyRelease(key);
	}
	
	@Override
	protected void remove() {
		
		this.listeners.forEach(listener -> listener.onRemove());
		this.stopListening();
	}
	
	/**
	 * @return an unmodifiable list of all {@linkplain KeyboardListener}s that are attached to this keyboard
	 * @since 1.0.0
	 */
	public List<KeyboardListener> getKeyboardListeners() {
		
		return Collections.unmodifiableList(this.listeners);
	}
	
	/**
	 * Adds a {@linkplain KeyboardListener}.
	 * @param listener the {@linkplain KeyboardListener}
	 * @since 1.0.0
	 */
	public void addKeyboardListener(KeyboardListener listener) {
		
		this.listeners.add(listener);
	}
	
	/**
	 * Removes a {@linkplain KeyboardListener}.
	 * @param listener the {@linkplain KeyboardListener}
	 * @since 1.0.0
	 */
	public void removeKeyboardListener(KeyboardListener listener) {
		
		this.listeners.remove(listener);
	}
	
	/**
	 * Removes a {@linkplain KeyboardListener}.
	 * @param index the index of the {@linkplain KeyboardListener}
	 * @since 1.0.0
	 */
	public void removeKeyboardListener(int index) {
		
		this.listeners.remove(index);
	}
	
	/**
	 * @param index the index of the {@linkplain KeyboardListener}
	 * @return the {@linkplain KeyboardListener} with the given index
	 * @since 1.0.0
	 */
	public KeyboardListener getKeyboardListener(int index) {
		
		return this.listeners.get(index);
	}
	
	/**
	 * @param key the key code
	 * @return {@code true} if the key with the corresponding key code is currently down, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isKeyDown(int key) {
		
		return this.downKeys[key];
	}

	@Override
	protected void onEvent(Event event) {
		
		Component component = event.getComponent();
		Identifier id = component.getIdentifier();
		
		if(KeyboardKeyMapping.isValidKey(id)) {
			
			int keyCode = KeyboardKeyMapping.getMap().get(id);
			float value = event.getValue();
			KeyboardEvent keyboardEvent = new KeyboardEvent(this, keyCode, component.getName());
			
			if(value == 1.0F) {
				
				this.listeners.forEach(listener -> listener.onKeyPress(keyboardEvent));
				this.downKeys[keyCode] = true;
				
			} else {
				
				this.listeners.forEach(listener -> listener.onKeyRelease(keyboardEvent));
				this.downKeys[keyCode] = false;
			}
		}
	}
}
