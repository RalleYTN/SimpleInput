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

import static net.java.games.input.Component.Identifier.Button.*;

import java.util.Collections;
import java.util.Map;

import net.java.games.input.Component.Identifier;

import static de.ralleytn.simple.input.MouseEvent.*;

/**
 * Maps the JInput identifiers to the SimpleInput constants.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class MouseButtonMapping {

	private static final Object[] MAPPING = {
			
		// JInput , SimpleInput
		LEFT,   BUTTON_LEFT,
		MIDDLE, BUTTON_MIDDLE,
		RIGHT,  BUTTON_RIGHT
	};
	private static final Map<Identifier, Integer> MAP = Collections.unmodifiableMap(Util.toMap(MouseButtonMapping.MAPPING));
	
	private MouseButtonMapping() {}
	
	/**
	 * @return the button mapping
	 * @since 1.0.0
	 */
	public static final Map<Identifier, Integer> getMap() {
		
		return MouseButtonMapping.MAP;
	}
	
	/**
	 * @return the size for the boolean array that remembers which buttons are currently down
	 * @since 1.0.0
	 */
	public static final int getDownButtonArraySize() {
		
		return Util.getMaxValue(MouseButtonMapping.MAP.values()) + 1;
	}
	
	/**
	 * @param id the JInput identifier
	 * @return {@code true} if the button for the JInput identifier is in the mapping
	 * @since 1.0.0
	 */
	public static final boolean isValidButton(Identifier id) {
		
		return MouseButtonMapping.MAP.containsKey(id);
	}
}
