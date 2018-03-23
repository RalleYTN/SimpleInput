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

import de.ralleytn.simple.input.GamepadEvent;
import de.ralleytn.simple.input.GamepadListener;

public class XInputGamepadListenerTestFrame extends JFrame implements GamepadListener {

	private static final long serialVersionUID = -2912829526355959130L;
	
	private static final String[] BUTTON_NAMES = {
		
		"X", "Y", "A", "B",
		"START", "SELECT",
		"R1", "L1",
	};

	@Override
	public void onAnalogStickPush(GamepadEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onButtonPress(GamepadEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onButtonRelease(GamepadEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPOVPress(GamepadEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPOVRelease(GamepadEvent event) {
		// TODO Auto-generated method stub
		
	}
}
