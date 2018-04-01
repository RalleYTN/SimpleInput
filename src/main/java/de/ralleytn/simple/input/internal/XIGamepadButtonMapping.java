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
import static de.ralleytn.simple.input.Direction.*;
import static net.java.games.input.Component.Identifier.Button.*;

import java.util.Collections;
import java.util.Map;

import de.ralleytn.simple.input.Direction;
import net.java.games.input.Component.Identifier;

/**
 * Maps the JInput identifiers to the SimpleInput constants.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.1.0
 * @since 1.0.0
 */
public final class XIGamepadButtonMapping {

	private static final Object[] DEFAULT_MAPPING = {
			
		// JInput   , SimpleInput
		_0, BUTTON_A,
		_1, BUTTON_B,
		_2, BUTTON_X,
		_3, BUTTON_Y,
		_4, BUTTON_L1,
		_5, BUTTON_R1,
		_6, BUTTON_SELECT,
		_7, BUTTON_START,
		_8, BUTTON_L3,
		_9, BUTTON_R3,
	};
	private static final Object[] NONAV_MAPPING = {
			
		// JInput   , SimpleInput
		_0, BUTTON_A,
		_1, BUTTON_B,
		_2, BUTTON_X,
		_3, BUTTON_Y,
		_4, BUTTON_L1,
		_5, BUTTON_R1,
		_6, BUTTON_L3,
		_7, BUTTON_R3,
	};
	
	private static final Map<Identifier, Integer> DEFAULT_MAP = Collections.unmodifiableMap(Util.toMap(DEFAULT_MAPPING));
	private static final Map<Identifier, Integer> NONAV_MAP = Collections.unmodifiableMap(Util.toMap(NONAV_MAPPING));
	
	private XIGamepadButtonMapping() {}
	
	/**
	 * Gives the direction in which an analog stick is pushed.
	 * Had to put these methods to the gamepad mapping classes because it is different for DirectInput and XInput.
	 * XInput up and down is inverted to DirectInput.
	 * @param value the value
	 * @param x x value
	 * @param y y value
	 * @return the direction in which the analog stick is pushed
	 * @since 1.1.0
	 */
	public static final Direction getAnalogStickDirection(float value, float x, float y) {
		
		if(value != 0.0F) {
			
			double angle = Math.toDegrees(Math.atan2(y, x));
			
			       if(angle == -90.0F) {return SOUTH;
			} else if(angle == 0.0F)   {return EAST;
			} else if(angle == 180.0F) {return WEST;
			} else if(angle == 90.0F)  {return NORTH;
			} else if(angle < -90.0F)  {return SOUTH_WEST;
			} else if(angle < 0.0F)    {return SOUTH_EAST;
			} else if(angle > 90.0F)   {return NORTH_WEST;
			} else if(angle > 0.0F)    {return NORTH_EAST;
			}
		}
		
		return null;
	}
	
	/**
	 * @param nonav
	 * @return the button mapping
	 * @since 1.0.0
	 */
	public static final Map<Identifier, Integer> getMap(boolean nonav) {
		
		return nonav ? NONAV_MAP : DEFAULT_MAP;
	}
	
	/**
	 * @param nonav
	 * @return the size for the boolean array that remembers which buttons are currently down
	 * @since 1.0.0
	 */
	public static final int getDownButtonArraySize(boolean nonav) {
		
		return Util.getMaxValue(nonav ? NONAV_MAP.values() : DEFAULT_MAP.values()) + 1;
	}
	
	/**
	 * @param id the JInput identifier
	 * @param nonav
	 * @return {@code true} if the button for the JInput identifier is in the mapping
	 * @since 1.0.0
	 */
	public static final boolean isValidButton(Identifier id, boolean nonav) {
		
		return nonav ? NONAV_MAP.containsKey(id) : DEFAULT_MAP.containsKey(id);
	}
}
