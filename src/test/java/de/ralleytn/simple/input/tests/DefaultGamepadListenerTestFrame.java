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
package de.ralleytn.simple.input.tests;

import de.ralleytn.simple.input.GamepadEvent;

class DefaultGamepadListenerTestFrame extends GamepadListenerTestFrame {

	private static final long serialVersionUID = -2912829526355959130L;
	
	private static final String[] BUTTON_NAMES = {
		
		"X", "Y", "A", "B",
		"START", "SELECT",
		"R1", "R2", "R3",
		"L1", "L2", "L3"
	};
	
	public DefaultGamepadListenerTestFrame() {
		
		super(DefaultGamepadListenerTestFrame.BUTTON_NAMES);
	}

	@Override
	public void onButtonRelease(GamepadEvent event) {
		
		int button = event.getButton();
		
		       if(button == GamepadEvent.BUTTON_A)      {this.buttonCheckList.check("A");
		} else if(button == GamepadEvent.BUTTON_B)      {this.buttonCheckList.check("B");
		} else if(button == GamepadEvent.BUTTON_X)      {this.buttonCheckList.check("X");
		} else if(button == GamepadEvent.BUTTON_Y)      {this.buttonCheckList.check("Y");
		} else if(button == GamepadEvent.BUTTON_START)  {this.buttonCheckList.check("START");
		} else if(button == GamepadEvent.BUTTON_SELECT) {this.buttonCheckList.check("SELECT");
		} else if(button == GamepadEvent.BUTTON_R1)     {this.buttonCheckList.check("R1");
		} else if(button == GamepadEvent.BUTTON_R2)     {this.buttonCheckList.check("R2");
		} else if(button == GamepadEvent.BUTTON_R3)     {this.buttonCheckList.check("R3");
		} else if(button == GamepadEvent.BUTTON_L1)     {this.buttonCheckList.check("L1");
		} else if(button == GamepadEvent.BUTTON_L2)     {this.buttonCheckList.check("L2");
		} else if(button == GamepadEvent.BUTTON_L3)     {this.buttonCheckList.check("L3");
		}
	}
}
