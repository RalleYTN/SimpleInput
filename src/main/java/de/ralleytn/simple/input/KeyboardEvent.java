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

/**
 * Represents an event that was triggered by a keyboard.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class KeyboardEvent extends DeviceEvent {
	
	private final Identifier id;
	private final String keyName;
	
	/**
	 * @param device the device that fired this event
	 * @param id the identifier of the key that is associated with this event
	 * @param keyName the name of the key that is associated with this event
	 * @since 1.0.0
	 */
	public KeyboardEvent(Device device, Identifier id, String keyName) {
		
		super(device);
		
		this.id = id;
		this.keyName = keyName;
	}
	
	/**
	 * @return the key associated with this event
	 * @since 1.0.0
	 */
	public final int getKey() {
		
		return KeyboardEvent.getKeyCode(this.id);
	}
	
	/**
	 * @return the name of the key associated with this event (depends on localization)
	 * @since 1.0.0
	 */
	public final String getKeyName() {
		
		return this.keyName;
	}
	
	private static final int getKeyCode(Identifier id) {

		return 0;
	}
}
