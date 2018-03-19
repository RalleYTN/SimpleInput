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

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.ralleytn.simple.input.Direction;
import de.ralleytn.simple.input.GamepadEvent;
import de.ralleytn.simple.input.GamepadListener;

class GamepadListenerTestFrame extends JFrame implements GamepadListener {

	private static final long serialVersionUID = -2912829526355959130L;
	
	private static final String[] BUTTON_NAMES = {
		
		"X", "Y", "A", "B",
		"START", "SELECT",
		"R1", "R2", "R3",
		"L1", "L2", "L3"
	};
	
	private CheckList<String> buttonCheckList;
	private CheckList<Direction> povCheckList;
	private CheckList<Direction> leftAnalogStickCheckList;
	private CheckList<Direction> rightAnalogStickCheckList;
	
	public GamepadListenerTestFrame() {
		
		super("GamepadListener Test");
		
		Direction[] directions = Direction.values();
		this.buttonCheckList = new CheckList<>("Buttons", GamepadListenerTestFrame.BUTTON_NAMES);
		this.povCheckList = new CheckList<>("POV", directions);
		this.leftAnalogStickCheckList = new CheckList<>("Left Analog Stick", directions);
		this.rightAnalogStickCheckList = new CheckList<>("Right Analog Stick", directions);
		
		JPanel contentPane = new JPanel();
		contentPane.add(this.buttonCheckList.getPanel());
		contentPane.add(this.povCheckList.getPanel());
		contentPane.add(this.leftAnalogStickCheckList.getPanel());
		contentPane.add(this.rightAnalogStickCheckList.getPanel());
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(contentPane);
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	public CheckList<String> getButtonCheckList() {
		
		return this.buttonCheckList;
	}
	
	public CheckList<Direction> getPovCheckList() {
		
		return this.povCheckList;
	}
	
	public CheckList<Direction> getLeftAnalogStickCheckList() {
		
		return this.leftAnalogStickCheckList;
	}
	
	public CheckList<Direction> getRightAnalogStickCheckList() {
		
		return this.rightAnalogStickCheckList;
	}
	
	@Override public void onPOVPress(GamepadEvent event) {}
	@Override public void onButtonPress(GamepadEvent event) {}
	
	@Override
	public void onAnalogStickPush(GamepadEvent event) {
		
		int analogStick = event.getAnalogStick();
		Direction direction = event.getDirection();
		
		if(analogStick == GamepadEvent.ANALOG_STICK_LEFT) {
			
			this.leftAnalogStickCheckList.check(direction);
			
		} else if(analogStick == GamepadEvent.ANALOG_STICK_RIGHT) {
			
			this.rightAnalogStickCheckList.check(direction);
		}
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

	@Override
	public void onPOVRelease(GamepadEvent event) {
		
		this.povCheckList.check(event.getDirection());
	}
}
