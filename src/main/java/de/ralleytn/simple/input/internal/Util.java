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

import de.ralleytn.simple.input.KeyboardEvent;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Key;

/**
 * Contains utility methods for SimpleInput.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Util {

	private static final Robot ROBOT = Util.createRobot();
	
	private Util() {}
	
	/**
	 * @param id the jInput identifier
	 * @return the SimpleInput key code
	 * @since 1.0.0
	 */
	public static final int getKeyCode(Identifier id) {

			   if(Key.W.equals(id))      {return KeyboardEvent.KEY_W;
		} else if(Key.A.equals(id))      {return KeyboardEvent.KEY_A;
		} else if(Key.S.equals(id))      {return KeyboardEvent.KEY_S;
		} else if(Key.D.equals(id))      {return KeyboardEvent.KEY_D;
		} else if(Key.LSHIFT.equals(id)) {return KeyboardEvent.KEY_SHIFT;
		} else if(Key.SPACE.equals(id))  {return KeyboardEvent.KEY_SPACE;
		} else if(Key.UP.equals(id))     {return KeyboardEvent.KEY_ARROW_UP;
		} else if(Key.DOWN.equals(id))   {return KeyboardEvent.KEY_ARROW_DOWN;
		} else if(Key.LEFT.equals(id))   {return KeyboardEvent.KEY_ARROW_LEFT;
		} else if(Key.RIGHT.equals(id))  {return KeyboardEvent.KEY_ARROW_RIGHT;
		} else if(Key.RETURN.equals(id)) {return KeyboardEvent.KEY_ENTER;
		} else if(Key.BACK.equals(id))   {return KeyboardEvent.KEY_BACKSPACE;
		} else if(Key.DELETE.equals(id)) {return KeyboardEvent.KEY_DELETE;
		} else if(Key._0.equals(id))     {return KeyboardEvent.KEY_0;
		} else if(Key._1.equals(id))     {return KeyboardEvent.KEY_1;
		} else if(Key._2.equals(id))     {return KeyboardEvent.KEY_2;
		} else if(Key._3.equals(id))     {return KeyboardEvent.KEY_3;
		} else if(Key._4.equals(id))     {return KeyboardEvent.KEY_4;
		} else if(Key._5.equals(id))     {return KeyboardEvent.KEY_5;
		} else if(Key._6.equals(id))     {return KeyboardEvent.KEY_6;
		} else if(Key._7.equals(id))     {return KeyboardEvent.KEY_7;
		} else if(Key._8.equals(id))     {return KeyboardEvent.KEY_8;
		} else if(Key._9.equals(id))     {return KeyboardEvent.KEY_9;
		}
	
		return 0;
	}
	
	/**
	 * Sets the cursor position.
	 * @param x X coordinate in pixel
	 * @param y Y coordinate in pixel
	 * @since 1.0.0
	 */
	public static final void setCursorPosition(int x, int y) {
		
		Util.ROBOT.mouseMove(x, y);
	}
	
	private static final Robot createRobot() {
		
		try {
			
			return new Robot();
			
		} catch(AWTException exception) {
			
			return null;
		}
	}
}
