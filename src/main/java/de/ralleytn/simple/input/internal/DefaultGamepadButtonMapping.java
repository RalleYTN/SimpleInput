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

import de.ralleytn.simple.input.Direction;
import net.java.games.input.Component.Identifier;

import static de.ralleytn.simple.input.GamepadEvent.*;

/**
 * Maps the JInput identifiers to the SimpleInput constants.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.1.0
 * @since 1.0.0
 */
public final class DefaultGamepadButtonMapping {

	private static final Object[] MAPPING = {
			
		// JInput   , SimpleInput
		_0,           BUTTON_Y,
		Y,            BUTTON_Y,
		_1,           BUTTON_B,
		B,            BUTTON_B,
		_2,           BUTTON_A,
		A,            BUTTON_A,
		_3,           BUTTON_X,
		X,            BUTTON_X,
		_4,           BUTTON_L1, 
		LEFT_THUMB,   BUTTON_L1,
		_5,           BUTTON_R1,
		RIGHT_THUMB,  BUTTON_R1, 
		_6,           BUTTON_L2,
		LEFT_THUMB2,  BUTTON_L2,
		_7,           BUTTON_R2,
		RIGHT_THUMB2, BUTTON_R2,
		_8,           BUTTON_SELECT,
		SELECT,       BUTTON_SELECT,
		_9,           BUTTON_START,
		START,        BUTTON_START,
		_10,          BUTTON_L3,
		LEFT_THUMB3,  BUTTON_L3,
		_11,          BUTTON_R3,
		RIGHT_THUMB3, BUTTON_R3,
		_12,          BUTTON_MODE,
		MODE,         BUTTON_MODE
	};
	private static final Map<Identifier, Integer> MAP = Collections.unmodifiableMap(Util.toMap(DefaultGamepadButtonMapping.MAPPING));
	
	private DefaultGamepadButtonMapping() {}
	
	/**
	 * Moved from {@linkplain Gamepad} to {@linkplain DefaultGamepadButtonMapping}.
	 * @param value the value
	 * @param x x value
	 * @param y y value
	 * @return the direction in which the analog stick is pushed
	 * @since 1.0.0
	 */
	public static final Direction getAnalogStickDirection(float value, float x, float y) {
		
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
	
	/**
	 * @return the button mapping
	 * @since 1.0.0
	 */
	public static final Map<Identifier, Integer> getMap() {
		
		return DefaultGamepadButtonMapping.MAP;
	}
	
	/**
	 * @return the size for the boolean array that remembers which buttons are currently down
	 * @since 1.0.0
	 */
	public static final int getDownButtonArraySize() {
		
		return Util.getMaxValue(DefaultGamepadButtonMapping.MAP.values()) + 1;
	}
	
	/**
	 * @param id the JInput identifier
	 * @return {@code true} if the button for the JInput identifier is in the mapping
	 * @since 1.0.0
	 */
	public static final boolean isValidButton(Identifier id) {
		
		return DefaultGamepadButtonMapping.MAP.containsKey(id);
	}
}
