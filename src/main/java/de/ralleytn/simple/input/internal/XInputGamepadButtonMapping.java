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
package de.ralleytn.simple.input.internal;

import static de.ralleytn.simple.input.GamepadEvent.*;
import static net.java.games.input.Component.Identifier.Button.*;

import java.util.Collections;
import java.util.Map;

import net.java.games.input.Component.Identifier;

/**
 * Maps the JInput identifiers to the SimpleInput constants.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class XInputGamepadButtonMapping {

	private static final Object[] MAPPING = {
			
		// JInput   , SimpleInput
		_0,           BUTTON_A,
		Y,            BUTTON_A,
		_1,           BUTTON_B,
		B,            BUTTON_B,
		_2,           BUTTON_X,
		A,            BUTTON_X,
		_3,           BUTTON_Y,
		X,            BUTTON_Y,
		_4,           BUTTON_L1, 
		LEFT_THUMB,   BUTTON_L1,
		_5,           BUTTON_R1,
		RIGHT_THUMB,  BUTTON_R1, 
		_6,           BUTTON_SELECT,
		SELECT,       BUTTON_SELECT,
		_7,           BUTTON_START,
		START,        BUTTON_START,
		_8,           BUTTON_L3,
		LEFT_THUMB3,  BUTTON_L3,
		_9,           BUTTON_R3,
		RIGHT_THUMB3, BUTTON_R3,
	};
	private static final Map<Identifier, Integer> MAP = Collections.unmodifiableMap(Util.toMap(XInputGamepadButtonMapping.MAPPING));
	
	private XInputGamepadButtonMapping() {}
	
	/**
	 * @return the button mapping
	 * @since 1.0.0
	 */
	public static final Map<Identifier, Integer> getMap() {
		
		return XInputGamepadButtonMapping.MAP;
	}
	
	/**
	 * @return the size for the boolean array that remembers which buttons are currently down
	 * @since 1.0.0
	 */
	public static final int getDownButtonArraySize() {
		
		return Util.getMaxValue(XInputGamepadButtonMapping.MAP.values()) + 1;
	}
	
	/**
	 * @param id the JInput identifier
	 * @return {@code true} if the button for the JInput identifier is in the mapping
	 * @since 1.0.0
	 */
	public static final boolean isValidButton(Identifier id) {
		
		return XInputGamepadButtonMapping.MAP.containsKey(id);
	}
}
