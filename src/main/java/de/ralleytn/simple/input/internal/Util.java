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

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.ralleytn.simple.input.XIGamepad;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.Component.Identifier;

/**
 * Contains utility methods for SimpleInput.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.1.0
 * @since 1.0.0
 */
public final class Util {

	/**
	 * Globally used instance of {@linkplain Robot}
	 * @since 1.0.0
	 */
	public static final Robot ROBOT = Util.createRobot();
	
	private Util() {}
	
	/**
	 * Helper method for {@link XIGamepad#hasNoNavigation()}
	 * @param controller the controller for a {@linkplain XIGamepad}
	 * @return the same as XIGamepad#hasNoNavigation()
	 * @since 1.1.0
	 * @see XIGamepad#hasNoNavigation()
	 */
	public static final boolean hasNoNavigation(Controller controller) {
		
		return controller.getComponents().length == 14;
	}
	
	/**
	 * @param values a collection of integers
	 * @return the highest value in the given collection
	 * @since 1.0.0
	 */
	public static final int getMaxValue(Collection<Integer> values) {
		
		int max = 0;
		
		for(int value : values) {
			
			if(value > max) {
				
				max = value;
			}
		}
		
		return max;
	}
	
	/**
	 * Creates an instance of {@linkplain Map} based on an object array which will be used as button or key mapping. 
	 * @param mapping the object array
	 * @return the created instance of {@linkplain Map}
	 * @since 1.0.0
	 */
	public static final Map<Identifier, Integer> toMap(Object[] mapping) {
		
		Map<Identifier, Integer> map = new HashMap<>();
		
		for(int index = 0; index < mapping.length - 1; index += 2) {
			
			map.put((Identifier)mapping[index], (int)mapping[index + 1]);
		}
		
		return map;
	}
	
	/**
	 * If a gamepad has an RX, RY and a Z axis, it is probably an XInput gamepad.
	 * THis method assumes that the controller has the type {@link Type#GAMEPAD}.
	 * @param controller the abstract {@linkplain Controller} instance
	 * @return {@code true} if the given instance of {@linkplain Controller} is probably an XInput gamepad, else {@code false}
	 * @since 1.0.0
	 */
	public static final boolean isXInput(Controller controller) {

		return "XIController".equals(controller.getClass().getSimpleName());
	}

	private static final Robot createRobot() {
		
		try {
			
			return new Robot();
			
		} catch(AWTException exception) {
			
			// WILL NEVER HAPPEN!
			return null;
		}
	}
}
