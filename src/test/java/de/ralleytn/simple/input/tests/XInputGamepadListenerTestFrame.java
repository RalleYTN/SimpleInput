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
import de.ralleytn.simple.input.XIGamepadEvent;

import static de.ralleytn.simple.input.XIGamepadEvent.*;

import javax.swing.JPanel;

import de.ralleytn.simple.input.XIGamepadListener;

class XInputGamepadListenerTestFrame extends GamepadListenerTestFrame implements XIGamepadListener {

	private static final long serialVersionUID = -2912829526355959130L;
	
	private static final String[] BUTTON_NAMES = {
		
		"X", "Y", "A", "B",
		"START", "SELECT",
		"R1", "L1",
		"R3", "L3"
	};
	private static final Float[] TRIGGER_VALUES = {
			
		0.25F, 0.5F, 0.75F, 1.0F
	};
	
	private CheckList<Float> lt;
	private CheckList<Float> rt;

	public XInputGamepadListenerTestFrame() {
		
		super(BUTTON_NAMES);
		
		this.lt = new CheckList<>("LT", TRIGGER_VALUES);
		this.rt = new CheckList<>("RT", TRIGGER_VALUES);
		
		JPanel contentPane = (JPanel)this.getContentPane();
		contentPane.add(this.lt.getPanel());
		contentPane.add(this.rt.getPanel());
		
		this.pack();
	}
	
	public CheckList<Float> getLTCheckList() {
		
		return this.lt;
	}
	
	public CheckList<Float> getRTCheckList() {
		
		return this.rt;
	}

	@Override
	public void onButtonRelease(GamepadEvent event) {
		
		int button = event.getButton();
		
		       if(button == BUTTON_A)      {this.buttonCheckList.check("A");
		} else if(button == BUTTON_B)      {this.buttonCheckList.check("B");
		} else if(button == BUTTON_X)      {this.buttonCheckList.check("X");
		} else if(button == BUTTON_Y)      {this.buttonCheckList.check("Y");
		} else if(button == BUTTON_START)  {this.buttonCheckList.check("START");
		} else if(button == BUTTON_SELECT) {this.buttonCheckList.check("SELECT");
		} else if(button == BUTTON_R1)     {this.buttonCheckList.check("R1");
		} else if(button == BUTTON_R3)     {this.buttonCheckList.check("R3");
		} else if(button == BUTTON_L1)     {this.buttonCheckList.check("L1");
		} else if(button == BUTTON_L3)     {this.buttonCheckList.check("L3");
		}
	}

	@Override
	public void onTriggerPush(XIGamepadEvent event) {
		
		int trigger = event.getTrigger();
		float val = event.getIntensity();
		
		if(trigger == TRIGGER_LEFT) {
			
			if(val >= 0.25F) this.lt.check(0.25F);
			if(val >= 0.5F) this.lt.check(0.5F);
			if(val >= 0.75F) this.lt.check(0.75F);
			if(val >= 1F) this.lt.check(1F);
			
		} else if(trigger == TRIGGER_RIGHT) {
			
			if(val >= 0.25F) this.rt.check(0.25F);
			if(val >= 0.5F) this.rt.check(0.5F);
			if(val >= 0.75F) this.rt.check(0.75F);
			if(val >= 1F) this.rt.check(1F);
		}
	}
}
