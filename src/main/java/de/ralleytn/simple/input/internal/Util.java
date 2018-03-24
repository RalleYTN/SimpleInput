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

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Axis;

/**
 * Contains utility methods for SimpleInput.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Util {

	public static final Robot ROBOT = Util.createRobot();
	
	private Util() {}
	
	public static final int getMaxValue(Collection<Integer> values) {
		
		int max = 0;
		
		for(int value : values) {
			
			if(value > max) {
				
				max = value;
			}
		}
		
		return max;
	}
	
	public static final Map<Identifier, Integer> toMap(Object[] mapping) {
		
		Map<Identifier, Integer> map = new HashMap<>();
		
		for(int index = 0; index < mapping.length - 1; index += 2) {
			
			map.put((Identifier)mapping[index], (int)mapping[index + 1]);
		}
		
		return map;
	}
	
	/**
	 * 
	 * @param controller
	 * @return
	 * @since 1.0.0
	 */
	public static final boolean isXInput(Controller controller) {
		
		Type type = controller.getType();
		
		if(Type.GAMEPAD.equals(type)) {
			
			Component[] components = controller.getComponents();
			boolean hasXAxis = false;
			boolean hasYAxis = false;
			boolean hasZAxis = false;
			boolean hasRXAxis = false;
			boolean hasRYAxis = false;
			boolean hasPOV = false;
			
			for(Component component : components) {
				
				Identifier id = component.getIdentifier();
				
					   if(Axis.X.equals(id))   {hasXAxis = true;
				} else if(Axis.Y.equals(id))   {hasYAxis = true;
				} else if(Axis.RX.equals(id))  {hasRXAxis = true;
				} else if(Axis.RY.equals(id))  {hasRYAxis = true;
				} else if(Axis.Z.equals(id))   {hasZAxis = true;
				} else if(Axis.POV.equals(id)) {hasPOV = true;
				}
			}
			
			return hasPOV && hasRXAxis && hasRYAxis && hasXAxis && hasYAxis && hasZAxis;
		}
		
		return false;
	}

	private static final Robot createRobot() {
		
		try {
			
			return new Robot();
			
		} catch(AWTException exception) {
			
			return null;
		}
	}
}
